package ru.jts_dev.gameserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yorie, AN3O
 */
public class CommandHandler<TCommandType> implements ICommandHandler<TCommandType> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private ArrayList<TCommandType> commands;

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TCommandType> getCommandList() {
        if (commands == null) {
            commands = new ArrayList<>();
            for (Method method : getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(TextCommand.class)) {
                    String commandName = method.getAnnotation(TextCommand.class).value();

                    if (!commands.contains((TCommandType) commandName)) {
                        commands.add((TCommandType) commandName);
                    }
                } else if (method.isAnnotationPresent(NumericCommand.class)) {
                    Integer command = method.getAnnotation(NumericCommand.class).value();
                    if (!commands.contains((TCommandType) command)) {
                        commands.add((TCommandType) command);
                    }
                }
            }
            commands.trimToSize();
        }

        return commands;
    }
}
