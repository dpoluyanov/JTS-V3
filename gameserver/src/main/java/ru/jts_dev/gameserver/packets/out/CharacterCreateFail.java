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
    // "Your character creation has failed."
    public static final String REASON_CREATION_FAILED = "REASON_CREATION_FAILED";
    // "You cannot create another character. Please delete the existing character and try again."
    // Removes all settings that were selected (race, class, etc).
    public static final String REASON_TOO_MANY_CHARACTERS = "REASON_TOO_MANY_CHARACTERS";
    // "This name already exists."
    public static final String REASON_NAME_ALREADY_EXISTS = "REASON_NAME_ALREADY_EXISTS";
    // "Your title cannot exceed 16 characters in length. Please try again."
    public static final String REASON_16_ENG_CHARS = "REASON_16_ENG_CHARS";
    // "Incorrect name. Please try again."
    public static final String REASON_INCORRECT_NAME = "REASON_INCORRECT_NAME";
    // "Characters cannot be created from this server."
    public static final String REASON_CREATE_NOT_ALLOWED = "REASON_16_ENG_CHARS";
    // "Unable to create character. You are unable to create a new character on the selected server.
    // A restriction is in place which restricts users from creating characters on different servers
    // where no previous character exists. Please choose another server."
    public static final String REASON_CHOOSE_ANOTHER_SVR = "REASON_INCORRECT_NAME";

    public static Map<String, OutgoingMessageWrapper> ERRORS = new HashMap<>();

    static {
        ERRORS.put(REASON_CREATION_FAILED, new CharacterCreateFail(0x00));
        ERRORS.put(REASON_TOO_MANY_CHARACTERS, new CharacterCreateFail(0x01));
        ERRORS.put(REASON_NAME_ALREADY_EXISTS, new CharacterCreateFail(0x02));
        ERRORS.put(REASON_16_ENG_CHARS, new CharacterCreateFail(0x03));
        ERRORS.put(REASON_INCORRECT_NAME, new CharacterCreateFail(0x04));
        ERRORS.put(REASON_CREATE_NOT_ALLOWED, new CharacterCreateFail(0x05));
        ERRORS.put(REASON_CHOOSE_ANOTHER_SVR, new CharacterCreateFail(0x06));
    }

    private final int errorCode;

    private CharacterCreateFail(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        writeByte(0x10);
        writeInt(errorCode);
    }
}
