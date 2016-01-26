package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 08.12.15
 */
public final class LoginOk extends OutgoingMessageWrapper {
    public static final int MAGIC_CONSTANT = 0x000003ea;
    public static final int ZEROS_LENGTH = 28;
    private final int key1;
    private final int key2;

    public LoginOk(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public void write() {
        writeByte(0x03);
        writeInt(key1);
        writeInt(key2);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(MAGIC_CONSTANT);
        writeZero(ZEROS_LENGTH);
    }
}
