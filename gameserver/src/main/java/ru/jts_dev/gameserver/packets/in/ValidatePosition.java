package ru.jts_dev.gameserver.packets.in;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ValidateLocation;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.PlayerService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 11.01.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x59)
public class ValidatePosition extends IncomingMessageWrapper {
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private PlayerService playerService;

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
        GameCharacter player = playerService.getCharacterBy(getConnectionId());
        broadcastService.send(player, new ValidateLocation(player));
    }
}