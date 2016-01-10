package ru.jts_dev.gameserver.handlers;

import ru.jts_dev.gameserver.model.GameCharacter;

public interface IAdminCommandHandler {
    /**
     * this is the worker method that is called when someone uses an admin command.
     *
     * @param activeChar
     * @param command
     * @return command success
     */
    boolean useAdminCommand(String command, GameCharacter activeChar);

    /**
     * this method is called at initialization to register all the item ids automatically
     *
     * @return all known itemIds
     */
    String[] getAdminCommandList();
}