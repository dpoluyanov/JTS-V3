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

package ru.jts_dev.common.messaging;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

/**
 * @author Camelion
 * @since 09.12.15
 */
public class GameServerInfo implements Serializable {
    private static final long serialVersionUID = -4579722245610189116L;

    private final int serverId;
    private final InetAddress address;
    private final int port;
    private boolean ageLimit;
    private boolean pvp;
    private int onlinePlayers;
    private int maxPlayers;
    private boolean enabled;
    private int serverType;
    private boolean bracketsEnabled;

    public GameServerInfo(final int serverId, final InetAddress address, final int port) {
        this.serverId = serverId;
        this.address = address;
        this.port = port;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof GameServerInfo))
            return false;
        final GameServerInfo gameServerInfo = (GameServerInfo) obj;
        return serverId == gameServerInfo.serverId;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(serverId);
    }

    public final int getServerId() {
        return serverId;
    }

    public final InetAddress getAddress() {
        return address;
    }

    public final int getPort() {
        return port;
    }

    public final boolean isAgeLimit() {
        return ageLimit;
    }

    public final boolean isPvp() {
        return pvp;
    }

    public final int getOnlinePlayers() {
        return onlinePlayers;
    }

    public final int getMaxPlayers() {
        return maxPlayers;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final int getServerType() {
        return serverType;
    }

    public final boolean isBracketsEnabled() {
        return bracketsEnabled;
    }
}
