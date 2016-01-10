package ru.jts_dev.gameserver.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents list of available handlers for class.
 *
 * @author Yorie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerList {
    Class<? extends ICommandHandler>[] value();
}