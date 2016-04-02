package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 30.11.15
 */
public final class Init extends OutgoingMessageWrapper {
    private final int sessionId;
    private final byte[] scrambledRSAKey;
    private final byte[] blowfishKey;

    public Init(int sessionId, byte[] scrambledRSAKey, byte[] blowfishKey) {
        this.sessionId = sessionId;
        this.scrambledRSAKey = scrambledRSAKey;
        this.blowfishKey = blowfishKey;
    }

    @Override
    public void write() {
        writeByte(0x00);
        writeInt(sessionId);
        writeInt(0x0000c621);
        writeBytes(scrambledRSAKey);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
        writeBytes(blowfishKey);
        writeInt(0x04);
    }
}
