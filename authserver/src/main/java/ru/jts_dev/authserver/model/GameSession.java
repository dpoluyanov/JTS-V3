package ru.jts_dev.authserver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;

import java.security.KeyPair;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 03.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class GameSession {
    public final KeyPair RSAKeyPair;
    private final byte[] blowfishKey;
    private final int sessionId;
    private String connectionId;

    @Autowired
    private MessageChannel packetChannel;

    public GameSession(String connectionId, int sessionId, KeyPair RSAKeyPair, byte[] blowfishKey) {
        this.connectionId = connectionId;
        this.sessionId = sessionId;
        this.RSAKeyPair = RSAKeyPair;
        this.blowfishKey = blowfishKey;
    }

    public KeyPair getRSAKeyPair() {
        return RSAKeyPair;
    }

    public byte[] getBlowfishKey() {
        return blowfishKey;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void send(OutgoingMessageWrapper msg) {
        msg.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(msg);
    }
}
