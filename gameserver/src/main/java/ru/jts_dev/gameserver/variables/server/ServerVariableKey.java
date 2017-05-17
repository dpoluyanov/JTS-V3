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

package ru.jts_dev.gameserver.variables.server;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public class ServerVariableKey implements Serializable {
    private static final long serialVersionUID = -4003143477633996904L;

    private byte serverId;
    private ServerVariableType serverVariableType;

    public ServerVariableKey() {
    }

    public ServerVariableKey(byte serverId, ServerVariableType serverVariableType) {
        this.serverId = serverId;
        this.serverVariableType = serverVariableType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerVariableKey that = (ServerVariableKey) o;
        return serverId == that.serverId &&
                serverVariableType == that.serverVariableType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, serverVariableType);
    }
}
