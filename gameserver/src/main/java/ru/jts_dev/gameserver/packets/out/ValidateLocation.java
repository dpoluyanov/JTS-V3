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

    public ValidateLocation(int objectId, Vector3D location) {
        this.objectId = objectId;
        this.location = location;
    }

    public ValidateLocation(GameCharacter character) {
        objectId = character.getObjectId();
        location = character.getVector3D();
    }

    @Override
    public void write() {
        putByte(0x79);
        putInt(objectId);
        putInt((int) location.getX());
        putInt((int) location.getY());
        putInt((int) location.getZ());
        // TODO heading
        putInt(10);
    }
}
