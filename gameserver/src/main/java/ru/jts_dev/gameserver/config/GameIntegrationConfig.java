package ru.jts_dev.gameserver.config;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.common.packets.StaticOutgoingMessageWrapper;
import ru.jts_dev.common.tcp.ProtocolByteArrayLengthHeaderSerializer;
import ru.jts_dev.gameserver.packets.GameClientPacketHandler;
import ru.jts_dev.gameserver.packets.out.VersionCheck;
import ru.jts_dev.gameserver.util.Encoder;

import java.nio.ByteOrder;
import java.util.concurrent.Executors;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Configuration
@IntegrationComponentScan
public class GameIntegrationConfig {
    private static final Logger log = LoggerFactory.getLogger(GameIntegrationConfig.class);
    private final GameClientPacketHandler clientPacketHandler;
    private final Encoder encoder;
    @Value("${gameserver.port}")
    private int port;

    @Autowired
    public GameIntegrationConfig(GameClientPacketHandler clientPacketHandler, Encoder encoder) {
        this.clientPacketHandler = clientPacketHandler;
        this.encoder = encoder;
    }

    @Bean
    public TcpNioServerConnectionFactory gameConnectionFactory() {
        TcpNioServerConnectionFactory serverConnectionFactory = new TcpNioServerConnectionFactory(port);

        serverConnectionFactory.setDeserializer(new ProtocolByteArrayLengthHeaderSerializer());
        serverConnectionFactory.setSerializer(new ProtocolByteArrayLengthHeaderSerializer());

        return serverConnectionFactory;
    }

    @Bean
    public TcpReceivingChannelAdapter tcpIn(AbstractServerConnectionFactory connectionFactory) {
        TcpReceivingChannelAdapter gateway = new TcpReceivingChannelAdapter();
        gateway.setConnectionFactory(connectionFactory);
        gateway.setOutputChannel(tcpInputChannel());

        return gateway;
    }

    @Bean
    public MessageChannel tcpInputChannel() {
        return new DirectChannel();
    }

    /**
     * Ingoing message flow
     *
     * @return - complete message transformation flow
     */
    @Bean
    public IntegrationFlow recvFlow() {
        return IntegrationFlows
                .from(tcpInputChannel())
                .transform(byte[].class, b -> wrappedBuffer(b).order(ByteOrder.LITTLE_ENDIAN))
                // no crypt for RequestProtocolVersion
                .route(ByteBuf.class, b -> b.readableBytes() > 0 && b.getByte(0) == 0x0E,
                        invoker -> invoker
                                .subFlowMapping("true",
                                        sf -> sf.transform(b -> b))
                                .subFlowMapping("false",
                                        sf -> sf.transform(encoder, "decrypt")))
                .transform(clientPacketHandler, "handle")
                .channel(incomingPacketExecutorChannel())
                .get();
    }

    @Bean
    public MessageChannel incomingPacketExecutorChannel() {
        // TODO: 07.12.15 investigate, may be should replace with spring TaskExecutor
        return new ExecutorChannel(Executors.newCachedThreadPool());
    }

    @ServiceActivator(inputChannel = "incomingPacketExecutorChannel")
    public void executePacket(IncomingMessageWrapper msg) {
        msg.prepare();
        msg.run();

        if (log.isDebugEnabled() && msg.getPayload().readableBytes() > 0) {
            final StringBuilder leftStr = new StringBuilder("[");
            msg.getPayload().forEachByte(
                    msg.getPayload().readerIndex(),
                    msg.getPayload().readableBytes(),
                    b -> {
                        leftStr.append(" ");
                        leftStr.append(String.format("%02X", b));
                        return true;
                    });
            leftStr.append(" ]");

            log.debug(msg.getPayload().readableBytes() + " byte(s) left in "
                    + msg.getClass().getSimpleName() + " buffer: "
                    + leftStr);
        }
        msg.release();
    }

    /**
     * Channel for raw object messages, unwrited and unencrypted
     *
     * @return - channel
     */
    @Bean
    public MessageChannel packetChannel() {
        return new DirectChannel();
    }

    /**
     * Channel for outgoing packets
     *
     * @return - channel
     */
    @Bean
    public MessageChannel tcpOutChannel() {
        return new PublishSubscribeChannel();
    }

    /**
     * Endpoint for output messages.
     * Receives message from tcpOutputChannel, and send it to client with corresponding {@link IpHeaders#CONNECTION_ID}
     *
     * @param connectionFactory - server factory bean
     * @return - tcp message handler bean
     */
    @Bean
    @ServiceActivator(inputChannel = "tcpOutChannel")
    public TcpSendingMessageHandler tcpOut(AbstractServerConnectionFactory connectionFactory) {
        TcpSendingMessageHandler gateway = new TcpSendingMessageHandler();
        gateway.setConnectionFactory(connectionFactory);

        return gateway;
    }

    /**
     * Outgoing message flow
     * // TODO rewrite to reactive
     *
     * @return - complete message transformations flow
     */
    @Bean
    public IntegrationFlow sendFlow(@Qualifier("packetChannel") MessageChannel packetChannel,
                                    @Qualifier("tcpOutChannel") MessageChannel tcpOutputChannel) {
        return IntegrationFlows
                .from(packetChannel)
                .route(OutgoingMessageWrapper.class, msg -> msg.isStatic(),
                        invoker -> invoker
                                .subFlowMapping("true",
                                        sf -> sf.transform(StaticOutgoingMessageWrapper.class,
                                                msg -> {
                                                    try {
                                                        return msg.clone();
                                                    } catch (CloneNotSupportedException e) {
                                                        // just rethrow to unchecked
                                                        throw new RuntimeException(e);
                                                    }
                                                }))
                                .subFlowMapping("false",
                                        sf -> sf.transform(OutgoingMessageWrapper.class, msg -> msg))
                )
                .transform(OutgoingMessageWrapper.class, msg -> {
                    msg.write();
                    return msg;
                })
                .route(OutgoingMessageWrapper.class, msg -> msg instanceof VersionCheck, // TODO: 14.12.15 unencrypted LoginFail
                        invoker -> invoker
                                .subFlowMapping("true",
                                        sf -> sf.transform(OutgoingMessageWrapper.class, OutgoingMessageWrapper::getPayload))
                                .subFlowMapping("false",
                                        sf -> sf
                                                .transform(OutgoingMessageWrapper.class, OutgoingMessageWrapper::getPayload)
                                                .transform(encoder, "encrypt")))
                .transform(ByteBuf.class, buf -> {
                    byte[] data = new byte[buf.readableBytes()];
                    buf.readBytes(data);
                    buf.release();
                    return data;
                })
                .channel(tcpOutputChannel)
                .get();
    }
}
