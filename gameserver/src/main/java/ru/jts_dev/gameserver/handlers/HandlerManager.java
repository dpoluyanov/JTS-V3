package ru.jts_dev.gameserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Handler manager manages command handlers: string and int commands sent by client.
 * Definition of manager contains definitions for type of command handlers and type of command itself.
 * Manager allows to register, remove and lookup handlers.
 * <p>
 * Instances of handler manager must define {link @HandlerList} annotation on self where list of all command handlers is described.
 * Handlers itself must define {link @TextCommand} for each command they implements.
 * {link @TextCommand} annotation placed on method that implements command.
 * Each method implementing command should receive {link @HandlerParams} argument for catching default params sent to handler from core.
 * <p>
 * See manager & handler implementations for more info and experience.
 *
 * @param <TCommandType> Command type.
 * @param <THandlerType> Handler type.
 * @author Yorie, AN3O
 */
@Component
public class HandlerManager<TCommandType, THandlerType extends ICommandHandler<TCommandType>>
        implements IHandlerManager<TCommandType, THandlerType> {
    @Autowired
    private ApplicationContext context;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final Map<TCommandType, THandlerType> handlers = new HashMap<>();
    protected final Map<TCommandType, Method> methods = new HashMap<>();
    protected final Set<TCommandType> disabledCommands = new HashSet<>();

    protected HandlerManager() {
        if (!getClass().isAnnotationPresent(HandlerList.class)) {
            log.error("Class [{}] should be annotated with [{}]!.", getClass().getName(), HandlerList.class.getName());
        }
    }

    /**
     * Receives handler class type and tries to create instance of it.
     *
     * @param cls Handler class.
     * @return Handler instance.
     */
    protected THandlerType getHandlerInstance(Class<? extends THandlerType> cls) {
        try {
            return context.getBean(cls);
        } catch (ClassCastException e) {
            log.error("The class [" + cls.getName() + "] is not a subclass of [" + ICommandHandler.class.getSimpleName() + "].");
        } catch (ExceptionInInitializerError e) {
            log.error("Invoking of constructor for handler [" + cls.getName() + "] produced error.", e);
        } catch (BeansException e) {
            log.error("Bean could not be created [" + cls.getName() + "].", e);
        } catch (RuntimeException e) {
            log.error("Failed to instantiate handler of class [" + cls.getName() + "].", e);
        }

        return null;
    }

    protected List<Method> getAnnotatedMethods(Class<? extends ICommandHandler<TCommandType>> handlerClass,
                                               Class<? extends Annotation> annotationClass) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : handlerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                methods.add(method);
            }
        }
        methods.trimToSize();
        return methods;
    }

    protected void addHandler(TCommandType command, THandlerType handler, Method method) {
        handlers.put(command, handler);
        methods.put(command, method);
    }

    @Override
    public int size() {
        return handlers.size();
    }

    @Override
    public void removeCommand(TCommandType command) {
        handlers.remove(command);
        methods.remove(command);
    }

    @Override
    public void disableCommand(TCommandType command) {
        disabledCommands.add(command);
    }

    @Override
    public void enableCommand(TCommandType command) {
        disabledCommands.remove(command);
    }

    @Override
    public boolean isCommandEnabled(TCommandType command) {
        return disabledCommands.contains(command);
    }

    @Override
    public boolean execute(HandlerParams<TCommandType> params) {
        TCommandType command = params.getCommand();

        if (isCommandEnabled(command)) {
            log.info("Execution of disabled command [{}] prevented. Please, enable command and try again.", command);
            return true;
        }

        ICommandHandler<TCommandType> handler = handlers.get(command);
        Method handlerMethod = methods.get(command);

        if (handler == null || handlerMethod == null) {
            return false;
        }

        if (!handler.isActive()) {
            log.info("Execution of inactive handler of command [{}] prevented.", command);
            return true;
        }

        try {
            return Boolean.TRUE.equals(handlerMethod.invoke(handler, params));
        } catch (Exception e) {
            log.error("Failed to execute command [{}].", command, e);
            return true;
        }
    }
}
