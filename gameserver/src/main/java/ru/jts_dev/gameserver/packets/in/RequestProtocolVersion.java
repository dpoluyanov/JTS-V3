package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.VersionCheck;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x0E)
public class RequestProtocolVersion extends IncomingMessageWrapper {
    private int version;

    @Autowired
    private GameSessionService sessionService;

    @Override
    public void prepare() {
        version = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        byte[] key = session.getDecryptKey().copy(0, 8).array();
        if (key.length != 8)
            throw new IndexOutOfBoundsException("client part of key must be 8 byte");

        // TODO: 13.12.15 check protocol version compatibility
        sessionService.send(session, new VersionCheck(key));
    }
}
