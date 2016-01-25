package ru.jts_dev.gameserver.packets.in.movement;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.movement.FinishRotating;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Opcode(0x5C)
public class FinishRotatingC extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BroadcastService broadcastService;

    private int degree;
    private int unknown; // TODO

    @Override
    public void prepare() {
        degree = readInt();
        unknown = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        // TODO broadcastService.broadcast(character, new FinishRotating(character, degree, 0));
        broadcastService.send(session, new FinishRotating(character, degree, 0));
    }
}
