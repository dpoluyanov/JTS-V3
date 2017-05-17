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
import ru.jts_dev.gameserver.packets.Opcode;
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
        /*final GameSession session = sessionService.getSessionBy(getConnectionId());
        final GameCharacter character = playerService.getCharacterBy(getConnectionId());

        final int clientHeading = rotationUtils.convertAngleToClientHeading((int) character.getAngle());
        broadcastService.send(session, new ValidateLocation(character, clientHeading));*/
    }
}