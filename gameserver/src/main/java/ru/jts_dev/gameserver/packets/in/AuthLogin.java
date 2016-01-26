package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterSelectionInfo;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.GameSessionService.AccountEvent;

import java.util.List;

/**
 * @author Camelion
 * @since 13.12.15
 */
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
    private BroadcastService broadcastService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private GameServerConfig gameServerConfig;

    @Override
    public final void prepare() {
        login = readString();
        playKey2 = readInt();
        playKey1 = readInt();
        loginKey1 = readInt();
        loginKey2 = readInt();
        languageType = readInt();
    }

    @Override
    public final void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        session.setPlayKey(playKey1);

        publisher.publishEvent(new AccountEvent(getConnectionId(), login));

        final List<GameCharacter> characters = repository.findAllByAccountName(login);

        // TODO: 13.12.15 additional playkey check with authserver session keys
        broadcastService.send(session,
                new CharacterSelectionInfo(characters, session.getPlayKey(), gameServerConfig.isCharCreationDisabled()));
    }
}
