package ru.jts_dev.gameserver.variables.server;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public class ServerVariableKey implements Serializable {
    private static final long serialVersionUID = -4003143477633996904L;

    private int serverId;
    private ServerVariableType serverVariableType;

    public ServerVariableKey() {
    }

    public ServerVariableKey(int serverId, ServerVariableType serverVariableType) {
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
