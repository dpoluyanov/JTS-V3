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
