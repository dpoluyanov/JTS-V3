package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterSelected;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService.CharacterSelectedEvent;

import java.util.List;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Opcode(0x12)
public class CharacterSelect extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;

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
        // TODO: 03.01.16 send SsqInfo packet
        GameSession session = sessionService.getSessionBy(getConnectionId());

        String account = sessionService.getAccountBy(getConnectionId());
        List<GameCharacter> characters = repository.findAllByAccountName(account);

        if (characterIndex < 0 || characters.isEmpty() || characters.size() <= characterIndex) {
            sessionService.forcedClose(session);
            return;
        }

        GameCharacter character = characters.get(characterIndex);

        publisher.publishEvent(new CharacterSelectedEvent(getConnectionId(), character));

        broadcastService.send(session, new CharacterSelected(character, session.getPlayKey()));
    }
}
