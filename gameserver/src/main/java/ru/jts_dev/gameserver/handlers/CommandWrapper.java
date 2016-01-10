package ru.jts_dev.gameserver.handlers;

import java.util.List;
import java.util.Map;

/**
 * @author AN3O
 */
public class CommandWrapper {
    private final List<String> args;
    private final Map<String, String> queryArgs;
    private String command;

    public CommandWrapper(String command, List<String> args, Map<String, String> queryArgs) {
        this.command = command;
        this.args = args;
        this.queryArgs = queryArgs;
    }

    /**
     * @return Command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Allows to replace command name and keep old arguments.
     *
     * @param command New command name.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getArgs() {
        return args;
    }

    public Map<String, String> getQueryArgs() {
        return queryArgs;
    }
}
