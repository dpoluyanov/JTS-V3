package ru.jts_dev.gameserver.packets.out;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 * @since 11.01.2016
 */
public class MoveToLocation extends OutgoingMessageWrapper {
    private final int objectId;
    private final Vector3D location;
    private final Vector3D end;

    public MoveToLocation(final GameCharacter character, final Vector3D end) {
        objectId = character.getObjectId();
        location = character.getVector3D();
        this.end = end;
    }

    @Override
    public void write() {
        writeByte(0x2F);

        writeInt(objectId);

        writeInt((int) end.getX());
        writeInt((int) end.getY());
        writeInt((int) end.getZ());

        writeInt((int) location.getX());
        writeInt((int) location.getY());
        writeInt((int) location.getZ());
    }
}