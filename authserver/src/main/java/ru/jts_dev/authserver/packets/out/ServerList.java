package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.messaging.GameServerInfo;

import java.util.Set;

/**
 * @author Camelion
 * @since 09.12.15
 */
public class ServerList extends OutgoingMessageWrapper {
    private final Set<GameServerInfo> gameServers;

    public ServerList(Set<GameServerInfo> gameServers) {
        this.gameServers = gameServers;
    }

    @Override
    public void write() {
        putByte(0x04);
        putByte(gameServers.size() % Byte.MAX_VALUE);
        putByte(0x00); // TODO: 09.12.15 Last Server

        for (GameServerInfo server : gameServers) {
            putByte(server.getServerId());
            putBytes(server.getAddress().getAddress());
            putInt(server.getPort());
            putByte(server.isAgeLimit());
            putByte(server.isPvp());
            putShort(server.getOnlinePlayers());
            putShort(server.getMaxPlayers());
            putByte(server.isEnabled());
            putInt(server.getServerType());
            putByte(server.isBracketsEnabled());
        }
    }
}
