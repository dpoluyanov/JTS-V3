package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 11.12.15
 */
public class PlayOk extends OutgoingMessageWrapper {
    private final int key1;
    private final int key2;

    public PlayOk(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public void write() {
        putByte(0x07);
        putInt(key1);
        putInt(key2);
    }
}
