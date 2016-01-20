package ru.jts_dev.gameserver.handlers;

import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;

/**
 * Parameter container for chat command handlers only.
 *
 * @author Yorie
 */
public class ChatHandlerParams<Integer> extends HandlerParams<Integer> {
    private final String message;
    private final String target;

    public ChatHandlerParams(GameSession session, GameCharacter character, Integer command, String message, String target) {
        super(session, character, command);
        this.message = message;
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public String getTarget() {
        return target;
    }
}
