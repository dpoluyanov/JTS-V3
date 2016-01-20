package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterSelectionInfo;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastServiceTemp;
import ru.jts_dev.gameserver.service.GameSessionService;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x2B)
public class AuthLogin extends IncomingMessageWrapper {
    private String login;
    private int playKey1;
    private int playKey2;
    private int loginKey1;
    private int loginKey2;
    private int languageType;

    @Autowired
    private GameSessionService sessionService;

    @Autowired
    private BroadcastServiceTemp broadcastService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private GameServerConfig gameServerConfig;

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
        session.setPlayKey(playKey1);

        publisher.publishEvent(new GameSessionService.AccountEvent(getConnectionId(), login));

        List<GameCharacter> characters = repository.findAllByAccountName(login);

        // TODO: 13.12.15 additional playkey check with authserver session keys
        broadcastService.send(session,
                new CharacterSelectionInfo(characters, session.getPlayKey(), gameServerConfig.isCharCreationDisabled()));
    }
}
