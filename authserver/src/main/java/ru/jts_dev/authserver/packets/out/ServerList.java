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

package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.messaging.GameServerInfo;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import java.util.Set;

/**
 * @author Camelion
 * @since 09.12.15
 */
public class ServerList extends OutgoingMessageWrapper {
    private final Set<GameServerInfo> gameServers;

    public ServerList(final Set<GameServerInfo> gameServers) {
        this.gameServers = gameServers;
    }

    @Override
    public void write() {
        writeByte(0x04);
        writeByte(gameServers.size());
        writeByte(0x00); // TODO: 09.12.15 Last Server

        for (final GameServerInfo server : gameServers) {
            writeByte(server.getServerId());
            writeBytes(server.getAddress().getAddress());
            writeInt(server.getPort());
            writeBoolean(server.isAgeLimit());
            writeBoolean(server.isPvp());
            writeShort(server.getOnlinePlayers());
            writeShort(server.getMaxPlayers());
            writeBoolean(server.isEnabled());
            writeInt(server.getServerType());
            writeBoolean(server.isBracketsEnabled());
        }

        writeShort(0x00);

        // TODO: 26.01.16 characters on server
        writeByte(0); //writeByte(charactersPerServerSize);
        // iter(charactersPerServer) {
        //     writeByte(serverId);
        //     writeByte(charactersCount);
        //     writeByte(charactersToDeleteFromThisServerSize);
        //     iter(charactersToDeleteFromThisServer) {
        //         writeInt(deleteTimeInSeconds);
        //     }
        //}
    }
}
