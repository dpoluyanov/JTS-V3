package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.CharacterSelectionInfo;
import ru.jts_dev.gameserver.service.GameSessionService;

import java.util.Collections;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class GotoLobby extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        session.send(new CharacterSelectionInfo(Collections.emptyList(), session.getPlayKey()));
    }
}
