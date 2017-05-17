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
    private final MovementService movementService;
    private final GameSessionService sessionService;
    private final PlayerService playerService;

    private static final int MAGIC_NUMBER = 20;
    private int targetX;
    private int targetY;
    private int targetZ;
    private int originX;
    private int originY;
    private int originZ;
    private int movementType;

    @Autowired
    public MoveBackwardToLocation(MovementService movementService, GameSessionService sessionService, PlayerService playerService) {
        this.movementService = movementService;
        this.sessionService = sessionService;
        this.playerService = playerService;
    }

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
