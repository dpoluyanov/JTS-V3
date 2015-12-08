package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.authserver.model.GameServerSession;
import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;

import java.util.List;

/**
 * @author Camelion
 * @since 09.12.15
 */
public class ServerList extends OutgoingMessageWrapper {
    private final List<GameServerSession> gameServers;

    public ServerList(List<GameServerSession> gameServers) {
        this.gameServers = gameServers;
    }

    @Override
    public void write() {
        putByte(0x04);
        putByte(gameServers.size() % Byte.MAX_VALUE);
        putByte(0x00); // TODO: 09.12.15 Last Server

        for (GameServerSession server : gameServers) {
            putByte(server.getServerId());
            putBytes(server.getAddress().getAddress());
            putInt(server.getPort());
            putByte(server.isAgeLimit());
            putByte(server.isPvP());
            putShort(server.getOnlinePlayers());
            putShort(server.getMaxPlayers());
            putByte(server.isEnabled());
            putInt(server.getServerType());
            putByte(server.isBracketsEnabled());
        }
    }
}
