/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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