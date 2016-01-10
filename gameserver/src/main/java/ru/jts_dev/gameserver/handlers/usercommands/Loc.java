package ru.jts_dev.gameserver.handlers.usercommands;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.CommandHandler;
import ru.jts_dev.gameserver.handlers.HandlerParams;
import ru.jts_dev.gameserver.handlers.NumericCommand;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * Location command handler.
 *
 * @author AN3O
 */
@Component
public class Loc extends CommandHandler<Integer> {
    @NumericCommand(0)
    public boolean location(HandlerParams<Integer> params) {
        GameCharacter activeChar = params.getCharacter();

        // TODO
        return true;
    }
}
