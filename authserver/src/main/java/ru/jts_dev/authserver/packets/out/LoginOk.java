package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 08.12.15
 */
public class LoginOk extends OutgoingMessageWrapper {
    private final int key1;
    private final int key2;

    public LoginOk(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public void write() {
        putByte(0x03);
        putInt(key1);
        putInt(key2);
        putInt(0x00);
        putInt(0x00);
        putInt(0x000003ea);
        putBytes(new byte[28]);
    }
}
