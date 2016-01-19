package ru.jts_dev.gameserver.parser.data.action;

import ru.jts_dev.gameserver.constants.ActionHandlerType;

/**
 * @author Camelion
 * @since 20.01.16
 */
public class Action {
    public final int id;
    public final ActionHandlerType handler;
    public final Object option;

    public Action(int id, ActionHandlerType handler, Object option) {
        this.id = id;
        this.handler = handler;
        this.option = option;
    }
}
