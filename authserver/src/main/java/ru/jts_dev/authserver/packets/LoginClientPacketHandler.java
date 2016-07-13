package ru.jts_dev.authserver.packets;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.packets.in.AuthGameGuard;
import ru.jts_dev.authserver.packets.in.RequestAuthLogin;
import ru.jts_dev.authserver.packets.in.RequestServerList;
import ru.jts_dev.authserver.packets.in.RequestServerLogin;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 06.12.15
 */
@Component
public class LoginClientPacketHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginClientPacketHandler.class);

    private final ApplicationContext context;

    @Autowired
    public LoginClientPacketHandler(ApplicationContext context) {
        this.context = context;
    }

    public final IncomingMessageWrapper handle(final ByteBuf buf, @Header(CONNECTION_ID) final String connectionId) {
        if (buf.readableBytes() == 0)
            throw new RuntimeException("At least 1 readable byte excepted in buffer");

        final int opcode = buf.readByte();
        final IncomingMessageWrapper msg;
        switch (opcode) {
            case 0x00:
                msg = context.getBean(RequestAuthLogin.class);
                break;
            case 0x02:
                msg = context.getBean(RequestServerLogin.class);
                break;
            case 0x05:
                msg = context.getBean(RequestServerList.class);
                break;
            case 0x07:
                msg = context.getBean(AuthGameGuard.class);
                break;
            default:
                throw new RuntimeException("Invalid packet opcode: " + Integer.toHexString(opcode));
        }

        final ByteBuf data = buf.slice();

        Class<? extends IncomingMessageWrapper> packetClass = msg.getClass();
        log.trace("received packet: {}, length: {}", packetClass.getSimpleName(), data.readableBytes());

        final MessageHeaders headers = msg.getHeaders();
        headers.put(CONNECTION_ID, connectionId);
        msg.setPayload(data);

        return msg;
    }
}
