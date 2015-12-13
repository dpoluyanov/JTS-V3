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
 * @since 13.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class AuthLogin extends IncomingMessageWrapper {
    private String login;
    private int playKey1;
    private int playKey2;
    private int loginKey1;
    private int loginKey2;
    private int languageType;

    @Autowired
    private GameSessionService sessionService;

    @Override
    public void prepare() {
        login = readString();
        playKey2 = readInt();
        playKey1 = readInt();
        loginKey1 = readInt();
        loginKey2 = readInt();
        languageType = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 13.12.15 additional playkey check with authserver session keys
        session.send(new CharacterSelectionInfo(Collections.emptyList(), playKey1));
    }
}
