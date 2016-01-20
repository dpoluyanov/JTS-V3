package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 20.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(second = 0x36)
public class GotoLobby extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastServiceTemp broadcastService;
    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private GameServerConfig gameServerConfig;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        String accountName = sessionService.getAccountBy(getConnectionId());

        List<GameCharacter> characters = repository.findAllByAccountName(accountName);

        broadcastService.send(session,
                new CharacterSelectionInfo(characters, session.getPlayKey(), gameServerConfig.isCharCreationDisabled()));
    }
}
