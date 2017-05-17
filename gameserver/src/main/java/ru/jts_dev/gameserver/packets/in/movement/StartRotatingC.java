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

package ru.jts_dev.gameserver.packets.in.movement;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.movement.StartRotating;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.util.RotationUtils;

import javax.inject.Inject;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Opcode(0x5B)
public class StartRotatingC extends IncomingMessageWrapper {
    private final GameSessionService sessionService;
    private final PlayerService playerService;
    private final BroadcastService broadcastService;
    private final RotationUtils rotationUtils;

    private int heading;
    private int side;

    @Inject
    public StartRotatingC(GameSessionService sessionService, RotationUtils rotationUtils, BroadcastService broadcastService, PlayerService playerService) {
        this.sessionService = sessionService;
        this.rotationUtils = rotationUtils;
        this.broadcastService = broadcastService;
        this.playerService = playerService;
    }

    @Override
    public void prepare() {
        heading = readInt();
        side = readInt(); // side (1 = right, -1 = left)
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        /*Rotation oldRotation = character.getRotation();
        double angle = rotationUtils.convertClientHeadingToAngle(heading);
        // TODO check that
        Rotation newRotation = rotationUtils.clientRotation(oldRotation, angle, side);
        character.setRotation(newRotation);*/
        double angle = rotationUtils.convertClientHeadingToAngle(heading);
        character.setAngle(angle);
        // TODO broadcastService.broadcast(character, new StartRotating(character, heading, side, 0));
        broadcastService.send(session, new StartRotating(character, heading, side, 0));
    }
}
