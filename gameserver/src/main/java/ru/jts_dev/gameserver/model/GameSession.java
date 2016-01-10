package ru.jts_dev.gameserver.model;

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class GameSession {
    private final String connectionId;
    private final ByteBuf encryptKey;
    private final ByteBuf decryptKey;

    @Autowired
    private MessageChannel packetChannel;

    private int playKey;

    public GameSession(String connectionId, ByteBuf encryptKey, ByteBuf decryptKey) {
        if (encryptKey.readableBytes() != 16)
            throw new RuntimeException("encryptKey must be 16 bytes");
        if (decryptKey.readableBytes() != 16)
            throw new RuntimeException("decryptKey must be 16 bytes");

        this.connectionId = connectionId;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    public void send(OutgoingMessageWrapper msg) {
        msg.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(msg);
    }

    public String getConnectionId() {
        return connectionId;
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
