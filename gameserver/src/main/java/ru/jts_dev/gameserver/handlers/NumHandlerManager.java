package ru.jts_dev.gameserver.handlers;

import java.lang.reflect.Method;

/**
 * Handler manager for numeric commands received from client.
 *
 * @author Yorie
 */
public class NumHandlerManager extends HandlerManager<Integer, ICommandHandler<Integer>> {
    /**
     * Adds all handlers for enumerated handler classes of manager.
     * Handler commands detected when {link @NumericCommand} annotation present on method.
     *
     * @param classes List of handler classes.
     */
    @SuppressWarnings("unchecked")
    protected void addHandlers(Class<? extends ICommandHandler>[] classes) {
        for (Class<? extends ICommandHandler> cls : classes) {
            try {
                Class<? extends ICommandHandler<Integer>> castedClass = (Class<? extends ICommandHandler<Integer>>) cls;
                ICommandHandler<Integer> handler = getHandlerInstance(castedClass);

                if (handler != null) {
                    for (Method method : getAnnotatedMethods(castedClass, NumericCommand.class)) {
                        Integer command = method.getAnnotation(NumericCommand.class).value();
                        addHandler(command, handler, method);
                    }
                }
            } catch (Exception e) {
                log.error("Cannot register handler for class [" + cls.getName() + "].", e);
            }
        }
    }
}
