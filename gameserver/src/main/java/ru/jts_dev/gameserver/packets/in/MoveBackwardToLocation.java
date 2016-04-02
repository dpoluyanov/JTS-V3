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
public final class MoveBackwardToLocation extends IncomingMessageWrapper {
    @Autowired
    private MovementService movementService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;

    public static final int MAGIC_NUMBER = 20;
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
        // TODO: 26.01.16 check (and remove?) this magic
        if (movementType == 1) {
            targetZ += MAGIC_NUMBER;
        }

        // TODO: 06.01.16
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final GameCharacter character = playerService.getCharacterBy(getConnectionId());
        final Vector3D end = new Vector3D(targetX, targetY, targetZ);

        movementService.stopMovement(character);
        movementService.moveTo(session, character, end);
    }
}
