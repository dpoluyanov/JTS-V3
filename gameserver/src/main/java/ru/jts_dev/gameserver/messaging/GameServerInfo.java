package ru.jts_dev.gameserver.messaging;

import java.net.InetAddress;
import java.util.Objects;

/**
 * @author Camelion
 * @since 09.12.15
 */
// TODO: 09.12.15 move to Spring Configuration and send from game server
public class GameServerInfo {
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

    public GameServerInfo(int serverId, InetAddress address, int port) {
        this.serverId = serverId;
        this.address = address;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GameServerInfo that = (GameServerInfo) o;
        return getServerId() == that.getServerId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerId());
    }

    public int getServerId() {
        return serverId;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean isAgeLimit() {
        return ageLimit;
    }

    public boolean isPvp() {
        return pvp;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getServerType() {
        return serverType;
    }

    public boolean isBracketsEnabled() {
        return bracketsEnabled;
    }
}
