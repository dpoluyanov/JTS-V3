package ru.jts_dev.authserver.model;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * @author Camelion
 * @since 09.12.15
 */
public class GameServerSession {
    private int serverId;
    private InetAddress address;
    private int port;
    private boolean ageLimit;
    private boolean pvP;
    private int onlinePlayers;
    private int maxPlayers;
    private boolean online;
    private int serverType;
    private boolean bracketsEnabled;

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

    public boolean isPvP() {
        return pvP;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }


    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isEnabled() {
        return online;
    }


    public int getServerType() {
        return serverType;
    }


    public boolean isBracketsEnabled() {
        return bracketsEnabled;
    }
}
