package ru.jts_dev.gameserver.packets.in;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.movement.MovementService;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.service.PlayerService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 17.12.2015
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x0F)
public class MoveBackwardToLocation extends IncomingMessageWrapper {
    private int targetX;
    private int targetY;
    private int targetZ;
    private int originX;
    private int originY;
    private int originZ;
    private int movementType;

    @Autowired
    private MovementService movementService;
    @Autowired
    private PlayerService playerService;

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
        GameCharacter character = playerService.getCharacterBy(getConnectionId());
        Vector3D end = new Vector3D(targetX, targetY, targetZ);

        movementService.moveTo(character, end);
    }
}
