package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Camelion
 * @since 24.12.15
 */
public class LoginFail extends OutgoingMessageWrapper {
    public static final String PASSWORD_DOES_NOT_MATCH_THIS_ACCOUNT = "PASSWORD_DOES_NOT_MATCH_THIS_ACCOUNT";

    public static final Map<String, LoginFail> ERRORS = new HashMap<>();

    static {
        ERRORS.put(PASSWORD_DOES_NOT_MATCH_THIS_ACCOUNT, new LoginFail(0x02));
    }

    private int errorCode;

    private LoginFail(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        writeByte(0x0A);
        writeInt(errorCode);
    }
}
