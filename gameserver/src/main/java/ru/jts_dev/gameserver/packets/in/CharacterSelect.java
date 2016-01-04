package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.CharacterSelected;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class CharacterSelect extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;

    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    private int characterIndex;
    private int unk1, unk2, unk3, unk4;

    @Override
    public void prepare() {
        characterIndex = readInt();
        unk1 = readShort();
        unk2 = readShort();
        unk3 = readShort();
        unk4 = readShort();
    }

    @Override
    public void run() {
        // TODO: 03.01.16 send SsqInfo and CharSelected packets
        GameSession session = sessionService.getSessionBy(getConnectionId());

        String account = sessionService.getAccountBy(getConnectionId());
        List<GameCharacter> characters = repository.findAllByAccountName(account);

        if (characterIndex < 0 || characters.isEmpty() || characters.size() <= characterIndex) {
            session.send(null); // TODO: 03.01.16 close connection
            return;
        }

        GameCharacter character = characters.get(characterIndex);

        publisher.publishEvent(new PlayerService.CharacterSelectedEvent(getConnectionId(), character));

        session.send(new CharacterSelected(character, session.getPlayKey()));
    }
}
