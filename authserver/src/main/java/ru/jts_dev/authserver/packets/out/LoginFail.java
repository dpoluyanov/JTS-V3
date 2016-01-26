package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 11.12.15
 */
public final class LoginFail extends OutgoingMessageWrapper {
    public static final int REASON_USER_OR_PASS_WRONG = 3;
    public static final int REASON_ACCESS_FAILED = 21;
    private final int code;

    public LoginFail(final int code) {
        this.code = code;
    }

    @Override
    public void write() {
        writeByte(0x01);
        writeInt(code);
    }
}
