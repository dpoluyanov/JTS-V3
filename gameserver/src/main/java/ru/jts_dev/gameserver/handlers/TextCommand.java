package ru.jts_dev.gameserver.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Text command for usage in command handlers.
 * If value of this annotation is empty string, then name of field or method will be taken as name of command.
 *
 * @author Yorie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TextCommand {
    String value() default "";
}
