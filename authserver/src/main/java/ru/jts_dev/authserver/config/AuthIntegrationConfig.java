package ru.jts_dev.authserver.config;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
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
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.packets.LoginClientPacketHandler;
import ru.jts_dev.authserver.packets.out.Init;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.util.Encoder;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.common.tcp.ProtocolByteArrayLengthHeaderSerializer;

import java.nio.ByteOrder;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.Executors;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.util.Collections.singletonMap;
import static ru.jts_dev.authserver.config.KeyGenerationConfig.scrambleModulus;
import static ru.jts_dev.authserver.util.Encoder.STATIC_KEY_HEADER;

/**
 * @author Camelion
 * @since 26.11.15
 */
@Configuration
@IntegrationComponentScan
public class AuthIntegrationConfig {
    private static final Logger log = LoggerFactory.getLogger(AuthIntegrationConfig.class);
    @Autowired
    private Encoder encoder;
    @Autowired
    private LoginClientPacketHandler clientPacketHandler;
    @Autowired
    private AuthSessionService authSessionService;

    /**
     * Server connection factory, for game client connections.
     * Set length serializer/deserializer for packets.
     *
     * @return - server factory bean
     */
    @Bean
    public TcpNioServerConnectionFactory connectionFactory() {
        TcpNioServerConnectionFactory serverConnectionFactory = new TcpNioServerConnectionFactory(2106);

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
     * Event listener for {@link TcpConnectionEvent}.
     * Event receives when new connection accepted.
     *
     * @param event - event instance
     */
    @EventListener
    public void authTcpConnectionEventListener(TcpConnectionEvent event) {
        String connectionId = event.getConnectionId();

        AuthSession gameSession = authSessionService.getSessionBy(connectionId);
        byte[] scrambledModulus = scrambleModulus(((RSAPublicKey) gameSession.getRsaKeyPair().getPublic()).getModulus());
        byte[] blowfishKey = gameSession.getBlowfishKey();

        OutgoingMessageWrapper msg = new Init(gameSession.getSessionId(), scrambledModulus, blowfishKey);
        msg.getHeaders().put(IpHeaders.CONNECTION_ID, event.getConnectionId());

        packetChannel().send(msg);
    }

    /**
     * Outgoing message flow
     *
     * @return - complete message transformations flow
     */
    @Bean
    public IntegrationFlow sendFlow() {
        return IntegrationFlows
                .from(packetChannel())
                .transform(OutgoingMessageWrapper.class, msg -> {
                    msg.write();
                    return msg;
                })
                .transform(OutgoingMessageWrapper.class, msg -> {
                    encoder.appendPadding(msg.getPayload());
                    return msg;
                })
                .route(OutgoingMessageWrapper.class, msg -> msg instanceof Init,
                        invoker -> invoker
                                .subFlowMapping("true",
                                        sf -> sf.transform(Init.class,
                                                i -> encoder.encWithXor(i.getPayload()))
                                                .enrichHeaders(singletonMap(STATIC_KEY_HEADER, true)))
                                .subFlowMapping("false",
                                        sf -> sf.transform(OutgoingMessageWrapper.class,
                                                OutgoingMessageWrapper::getPayload)))
                .transform(ByteBuf.class, buf -> encoder.appendChecksum(buf))
                .transform(ByteBuf.class, buf -> encoder.appendPadding(buf))
                .transform(ByteBuf.class, buf -> {
                    byte[] data = new byte[buf.readableBytes()];
                    buf.readBytes(data);
                    buf.release();
                    return data;
                })
                .transform(encoder, "encrypt")
                .headerFilter(STATIC_KEY_HEADER)
                .channel(tcpOutChannel())
                .get();
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
                .transform(encoder, "decrypt")
                .transform(byte[].class, b -> wrappedBuffer(b).order(ByteOrder.LITTLE_ENDIAN))
                .transform(ByteBuf.class, b -> encoder.validateChecksum(b))
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
                    + leftStr.toString());
        }

        msg.release();
    }
}
