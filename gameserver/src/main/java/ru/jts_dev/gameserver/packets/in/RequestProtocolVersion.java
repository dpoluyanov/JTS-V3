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

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.VersionCheck;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

/**
 * @author Camelion
 * @since 12.12.15
 */
@Opcode(0x0E)
public class RequestProtocolVersion extends IncomingMessageWrapper {
    @Autowired
    private GameServerConfig gameServerConfig;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;

    private int version;

    @Override
    public void prepare() {
        version = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        byte[] key = session.getDecryptKey().copy(0, 8).array();
        if (key.length != 8)
            throw new IndexOutOfBoundsException("client part of key must be 8 byte");

        // TODO: 13.12.15 check protocol version compatibility
        broadcastService.send(session, new VersionCheck(key, gameServerConfig.getServerId()));
    }
}
