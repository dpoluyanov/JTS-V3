package ru.jts_dev.gameserver.packets.in;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ValidateLocation;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.util.RotationUtils;

/**
 * @author Java-man
 * @since 11.01.2016
 */
@Opcode(0x59)
public class ValidatePosition extends IncomingMessageWrapper {
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private RotationUtils rotationUtils;

    private Vector3D location;
    private int heading;
    private int boatObjectId;

    @Override
    public void prepare() {
        location = new Vector3D(readInt(), readInt(), readInt());
        heading = readInt();
        boatObjectId = readInt();
    }

    @Override
    public void run() {
        // TODO
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final GameCharacter character = playerService.getCharacterBy(getConnectionId());

        final int clientHeading = rotationUtils.convertAngleToClientHeading(character.getAngle());
        broadcastService.send(session, new ValidateLocation(character, clientHeading));
    }
}