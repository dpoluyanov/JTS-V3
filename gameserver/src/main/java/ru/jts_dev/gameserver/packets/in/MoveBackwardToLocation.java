package ru.jts_dev.gameserver.packets.in;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.movement.MovementService;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;

/**
 * @author Java-man
 * @since 17.12.2015
 */
@Opcode(0x0F)
public class MoveBackwardToLocation extends IncomingMessageWrapper {
    @Autowired
    private MovementService movementService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;

    private int targetX;
    private int targetY;
    private int targetZ;
    private int originX;
    private int originY;
    private int originZ;
    private int movementType;

    @Override
    public void prepare() {
        targetX = readInt();
        targetY = readInt();
        targetZ = readInt();
        originX = readInt();
        originY = readInt();
        originZ = readInt();
        movementType = readInt(); // is 0 if cursor keys are used 1 if mouse is used
    }

    @Override
    public void run() {
        if (movementType == 1)
            targetZ += 27;

        // TODO: 06.01.16
        GameSession session = sessionService.getSessionBy(getConnectionId());
        GameCharacter character = playerService.getCharacterBy(getConnectionId());
        Vector3D end = new Vector3D(targetX, targetY, targetZ);

        movementService.moveTo(session, character, end);
    }
}
