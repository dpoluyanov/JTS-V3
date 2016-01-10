package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Handler manager for text commands.
 *
 * @author Yorie
 */
@Component
public class TextHandlerManager extends HandlerManager<String, ICommandHandler<String>> {
    /**
     * Adds all handlers for enumerated handler classes of manager.
     * Handler commands detected when {link @TextCommand} annotation present on method.
     *
     * @param classes List of handler classes.
     */
    @SuppressWarnings("unchecked")
    protected void addHandlers(Class<? extends ICommandHandler>[] classes) {
        for (Class<? extends ICommandHandler> cls : classes) {
            try {
                Class<? extends ICommandHandler<String>> castedClass = (Class<? extends ICommandHandler<String>>) cls;
                ICommandHandler<String> handler = getHandlerInstance(castedClass);

                if (handler != null) {
                    for (Method method : getAnnotatedMethods(castedClass, TextCommand.class)) {
                        String command = method.getAnnotation(TextCommand.class).value();

                        if (command.isEmpty()) {
                            command = method.getName();
                        }

                        command = command.toLowerCase();

                        addHandler(command, handler, method);
                    }
                }
            } catch (Exception e) {
                log.error("Cannot register handler for class [" + cls.getName() + "].", e);
            }
        }
    }

    @Override
    public boolean execute(HandlerParams<String> params) {
        params.setCommand(params.getCommand().toLowerCase());
        return super.execute(params);
    }
}
