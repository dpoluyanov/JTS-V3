package ru.jts_dev.gameserver.handlers;

import java.util.List;

/**
 * Interface for all command handlers.
 *
 * @author Yorie, AN3O
 */
public interface ICommandHandler<TCommandType> {
    /**
     * @return {@code true} если хэндлер активен
     */
    boolean isActive();

    /**
     * @return список команд, обслуживаемых этим хэндлером
     */
    List<TCommandType> getCommandList();
}
