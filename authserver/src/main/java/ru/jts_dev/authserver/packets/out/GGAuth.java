package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 08.12.15
 */
public final class GGAuth extends OutgoingMessageWrapper {
    private final int sessionId;

    public GGAuth(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void write() {
        writeByte(0x0B);
        writeInt(sessionId);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
    }
}
