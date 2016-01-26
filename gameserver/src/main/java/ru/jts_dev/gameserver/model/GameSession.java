package ru.jts_dev.gameserver.model;

import io.netty.buffer.ByteBuf;
import org.springframework.integration.ip.tcp.connection.TcpConnection;

/**
 * @author Camelion
 * @since 13.12.15
 */
public final class GameSession {
    private static final int KEY_SIZE = 16;
    private final TcpConnection connection;
    private final ByteBuf encryptKey;
    private final ByteBuf decryptKey;

    private int playKey;

    public GameSession(final TcpConnection connection, final ByteBuf encryptKey, final ByteBuf decryptKey) {
        if (encryptKey.readableBytes() != KEY_SIZE)
            throw new RuntimeException("encryptKey must be 16 bytes");
        if (decryptKey.readableBytes() != KEY_SIZE)
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

    public int getPlayKey() {
        return playKey;
    }

    public void setPlayKey(final int playKey) {
        this.playKey = playKey;
    }
}
