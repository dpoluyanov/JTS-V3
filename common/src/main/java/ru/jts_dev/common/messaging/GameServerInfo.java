package ru.jts_dev.common.messaging;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

/**
 * @author Camelion
 * @since 09.12.15
 */
// TODO: 09.12.15 move to Spring Configuration and send from game server
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
        if (obj == null || getClass() != obj.getClass())
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
