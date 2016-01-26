package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 11.12.15
 */
public final class PlayOk extends OutgoingMessageWrapper {
    private final int key1;
    private final int key2;

    public PlayOk(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public void write() {
        writeByte(0x07);
        writeInt(key1);
        writeInt(key2);
    }
}
