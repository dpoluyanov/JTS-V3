package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 08.12.15
 */
public class GGAuth extends OutgoingMessageWrapper {
    private int sessionId;

    public GGAuth(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void write() {
        putByte(0x0B);
        putInt(sessionId);
        putInt(0x00);
        putInt(0x00);
        putInt(0x00);
        putInt(0x00);
    }
}
