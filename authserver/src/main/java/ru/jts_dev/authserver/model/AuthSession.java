package ru.jts_dev.authserver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import java.security.KeyPair;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 03.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class AuthSession {
    public final KeyPair RSAKeyPair;
    private final byte[] blowfishKey;
    private final int sessionId;
    private final int loginKey1;
    private final int loginKey2;
    private final int gameKey1;
    private final int gameKey2;
    private String connectionId;
    @Autowired
    private MessageChannel packetChannel;

    public AuthSession(String connectionId, int sessionId, KeyPair RSAKeyPair, byte[] blowfishKey,
                       int loginKey1, int loginKey2, int gameKey1, int gameKey2) {
        this.connectionId = connectionId;
        this.sessionId = sessionId;
        this.RSAKeyPair = RSAKeyPair;
        this.blowfishKey = blowfishKey;
        this.loginKey1 = loginKey1;
        this.loginKey2 = loginKey2;
        this.gameKey1 = gameKey1;
        this.gameKey2 = gameKey2;
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

    public int getGameKey1() {
        return gameKey1;
    }

    public int getGameKey2() {
        return gameKey2;
    }
}
