package ru.jts_dev.authserver.packets;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.packets.in.AuthGameGuard;

/**
 * @author Camelion
 * @since 06.12.15
 */
@Component
public class LoginClientPacketHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginClientPacketHandler.class);

    @Autowired
    private ApplicationContext context;

    public IncomingMessageWrapper handle(ByteBuf buf) {
        if (buf.readableBytes() == 0)
            throw new RuntimeException("At least 1 readable byte excepted in buffer");

        int opcode = buf.readByte();
        IncomingMessageWrapper msg;
        switch (opcode) {
            case 0x07:
                msg = context.getBean(AuthGameGuard.class);
                break;
            default:
                throw new RuntimeException("Invalid packet opcode: " + Integer.toHexString(opcode));
        }

        ByteBuf data = buf.slice();

        log.trace("received packet: " + msg.getClass().getSimpleName() + ", length: " + data.readableBytes());

        msg.setPayload(data);

        return msg;
    }
}
