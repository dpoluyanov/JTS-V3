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
    private final int loginKey1;
    private final int loginKey2;
    private String connectionId;
    @Autowired
    private MessageChannel packetChannel;

    public GameSession(String connectionId, int sessionId, KeyPair RSAKeyPair, byte[] blowfishKey,
                       int loginKey1, int loginKey2) {
        this.connectionId = connectionId;
        this.sessionId = sessionId;
        this.RSAKeyPair = RSAKeyPair;
        this.blowfishKey = blowfishKey;
        this.loginKey1 = loginKey1;
        this.loginKey2 = loginKey2;
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

    public int getLoginKey2() {
        return loginKey2;
    }

    public int getLoginKey1() {
        return loginKey1;
    }
}