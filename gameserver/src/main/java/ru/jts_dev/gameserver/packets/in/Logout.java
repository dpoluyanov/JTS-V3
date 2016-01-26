package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.LeaveWorld;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Opcode(0x00)
public class Logout extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        // client close session by himself
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        broadcastService.send(session, LeaveWorld.PACKET);
        sessionService.forcedClose(session);
    }
}
