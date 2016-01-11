package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.LeaveWorld;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x00)
public class Logout extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        // client close session by himself
        GameSession session = sessionService.getSessionBy(getConnectionId());
        sessionService.send(session, LeaveWorld.PACKET);
        sessionService.forcedClose(session);
    }
}
