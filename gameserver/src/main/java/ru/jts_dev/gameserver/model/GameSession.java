package ru.jts_dev.gameserver.model;

import io.netty.buffer.ByteBuf;
import org.springframework.integration.ip.tcp.connection.TcpConnection;

/**
 * @author Camelion
 * @since 13.12.15
 */
public class GameSession {
    private final TcpConnection connection;
    private final ByteBuf encryptKey;
    private final ByteBuf decryptKey;

    private int playKey;

    public GameSession(TcpConnection connection, ByteBuf encryptKey, ByteBuf decryptKey) {
        if (encryptKey.readableBytes() != 16)
            throw new RuntimeException("encryptKey must be 16 bytes");
        if (decryptKey.readableBytes() != 16)
            throw new RuntimeException("decryptKey must be 16 bytes");

        this.connection = connection;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    public TcpConnection getConnection() {
        return connection;
    }

    public String getConnectionId() {
        return connection.getConnectionId();
    }

    public ByteBuf getEncryptKey() {
        return encryptKey;
    }

    public ByteBuf getDecryptKey() {
        return decryptKey;
    }

    public void setPlayKey(int playKey) {
        this.playKey = playKey;
    }

    public int getPlayKey() {
        return playKey;
    }
}
