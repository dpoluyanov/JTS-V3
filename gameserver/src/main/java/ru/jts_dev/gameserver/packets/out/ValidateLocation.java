package ru.jts_dev.gameserver.packets.out;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 * @since 11.01.2016
 */
public class ValidateLocation extends OutgoingMessageWrapper {
    private final int objectId;
    private final Vector3D location;
    private final int heading;

    public ValidateLocation(GameCharacter character, int clientHeading) {
        objectId = character.getObjectId();
        location = character.getVector3D();
        heading = clientHeading;
    }

    @Override
    public void write() {
        writeByte(0x79);

        writeInt(objectId);

        writeInt((int) location.getX());
        writeInt((int) location.getY());
        writeInt((int) location.getZ());
        writeInt(heading);
    }
}
