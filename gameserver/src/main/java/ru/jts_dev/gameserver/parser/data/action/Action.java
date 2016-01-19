package ru.jts_dev.gameserver.parser.data.action;

import ru.jts_dev.gameserver.constants.ActionHandlerType;

/**
 * @author Camelion
 * @since 20.01.16
 */
public final class Action {
    private final int id;
    private final ActionHandlerType handler;
    private final Object option;

    public Action(int id, ActionHandlerType handler, Object option) {
        this.id = id;
        this.handler = handler;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public ActionHandlerType getHandler() {
        return handler;
    }

    public Object getOption() {
        return option;
    }
}
