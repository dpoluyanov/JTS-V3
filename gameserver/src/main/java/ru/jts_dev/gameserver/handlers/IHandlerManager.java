package ru.jts_dev.gameserver.handlers;

/**
 * @author Yorie
 */
public interface IHandlerManager<TCommandType, THandlerType> {
    /**
     * @return Count of registered handlers in current handler manager.
     */
    int size();

    /**
     * Removes command for ever.
     *
     * @param command Command name.
     */
    void removeCommand(TCommandType command);

    /**
     * Disables command till it will be enabled.
     *
     * @param command Command name.
     */
    void disableCommand(TCommandType command);

    /**
     * Enable command if it was disabled.
     *
     * @param command Command name.
     */
    void enableCommand(TCommandType command);

    /**
     * @param command Command name.
     * @return True if given command is enabled.
     */
    boolean isCommandEnabled(TCommandType command);

    /**
     * Executes command with given params.
     *
     * @param params Params container.
     * @return True on successful command execution.
     */
    boolean execute(HandlerParams<TCommandType> params);
}
