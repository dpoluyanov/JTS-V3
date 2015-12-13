package ru.jts_dev.gameserver;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.in.AuthLogin;
import ru.jts_dev.gameserver.packets.in.RequestProtocolVersion;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Component
public class GameClientPacketHandler {
    private static final Logger log = LoggerFactory.getLogger(GameClientPacketHandler.class);
    @Autowired
    private ApplicationContext context;

    public IncomingMessageWrapper handle(ByteBuf buf, @Header(CONNECTION_ID) String connectionId) {
        if (buf.readableBytes() == 0)
            throw new RuntimeException("At least 1 readable byte excepted in buffer");

        int opcode = buf.readByte();
        IncomingMessageWrapper msg;
        switch (opcode) {
            case 0x0E:
                msg = context.getBean(RequestProtocolVersion.class);
                break;
            case 0x2B:
                msg = context.getBean(AuthLogin.class);
                break;
            default:
                throw new RuntimeException("Invalid packet opcode: " + String.format("0x%02X", (byte) opcode));
        }

        ByteBuf data = buf.slice();

        log.trace("received packet: " + msg.getClass().getSimpleName() + ", length: " + data.readableBytes());

        msg.getHeaders().put(CONNECTION_ID, connectionId);
        msg.setPayload(data);

        return msg;
    }
}
