package ru.jts_dev.authserver.packets;

/**
 * @author Camelion
 * @since 30.11.15
 */
public class Init extends MessageWrapper {
    private int sessionId;
    private byte[] scrambledRSAKey;
    private byte[] blowfishKey;

    public Init(int sessionId, byte[] scrambledRSAKey, byte[] blowfishKey) {
        this.sessionId = sessionId;
        this.scrambledRSAKey = scrambledRSAKey;
        this.blowfishKey = blowfishKey;
    }

    @Override
    public void write() {
        putByte(0x00);
        putInt(sessionId);
        putInt(0x0000c621);
        putBytes(scrambledRSAKey);
        putInt(0x29DD954E);
        putInt(0x77C39CFC);
        putInt(0x97ADB620);
        putInt(0x07BDE0F7);
        putBytes(blowfishKey);
        putByte(0x00);
    }
}
