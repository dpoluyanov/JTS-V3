package ru.jts_dev.gameserver.packets.in.movement;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.movement.StartRotating;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Opcode(0x5B)
public class StartRotatingC extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BroadcastService broadcastService;

    private int degree;
    private int side;

    @Override
    public void prepare() {
        degree = readInt();
        side = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        character.setRotation(new Rotation(Vector3D.ZERO, degree));
        // TODO broadcastService.broadcast(character, new StartRotating(character, degree, side, 0));
        broadcastService.send(session, new StartRotating(character, degree, side, 0));
    }
}
