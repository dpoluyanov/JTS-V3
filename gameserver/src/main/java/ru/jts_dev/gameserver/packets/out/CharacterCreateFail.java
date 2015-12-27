package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import javax.validation.Payload;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camelion
 * @since 21.12.15
 */
public class CharacterCreateFail extends OutgoingMessageWrapper implements Payload {
    public static final String REASON_CREATION_FAILED = "REASON_CREATION_FAILED";
    public static final String REASON_TOO_MANY_CHARACTERS = "REASON_TOO_MANY_CHARACTERS";
    public static final String REASON_NAME_ALREADY_EXISTS = "REASON_NAME_ALREADY_EXISTS";
    public static final String REASON_16_ENG_CHARS = "REASON_16_ENG_CHARS";
    public static final String REASON_INCORRECT_NAME = "REASON_INCORRECT_NAME";
    public static Map<String, OutgoingMessageWrapper> ERRORS = new HashMap<>();

    static {
        ERRORS.put(REASON_CREATION_FAILED, new CharacterCreateFail(0x00));
        ERRORS.put(REASON_TOO_MANY_CHARACTERS, new CharacterCreateFail(0x01));
        ERRORS.put(REASON_NAME_ALREADY_EXISTS, new CharacterCreateFail(0x02));
        ERRORS.put(REASON_16_ENG_CHARS, new CharacterCreateFail(0x03));
        ERRORS.put(REASON_INCORRECT_NAME, new CharacterCreateFail(0x04));
    }

    private final int errorCode;

    public CharacterCreateFail(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        putByte(0x10);
        putInt(errorCode);
    }
}
