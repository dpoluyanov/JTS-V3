package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.authserver.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 11.12.15
 */
public class LoginFail extends OutgoingMessageWrapper {
    public static final int REASON_USER_OR_PASS_WRONG = 3;
    public static final int REASON_ACCESS_FAILED = 21;
    private final int code;

    public LoginFail(int code) {
        this.code = code;
    }

    @Override
    public void write() {
        putByte(0x01);
        putInt(code);
    }
}
