package ru.jts_dev.authserver.model;

import java.security.KeyPair;

/**
 * @author Camelion
 * @since 03.12.15
 */
public class SessionKeys {
    public final KeyPair RSAKeyPair;
    private final byte[] blowfishKey;

    public SessionKeys(KeyPair RSAKeyPair, byte[] blowfishKey) {
        this.RSAKeyPair = RSAKeyPair;
        this.blowfishKey = blowfishKey;
    }

    public KeyPair getRSAKeyPair() {
        return RSAKeyPair;
    }

    public byte[] getBlowfishKey() {
        return blowfishKey;
    }
}
