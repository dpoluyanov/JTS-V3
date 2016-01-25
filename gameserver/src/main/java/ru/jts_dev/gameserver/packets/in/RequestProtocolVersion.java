package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.VersionCheck;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Opcode(0x0E)
public class RequestProtocolVersion extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;

    private int version;

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
        broadcastService.send(session, new VersionCheck(key));
    }
}
