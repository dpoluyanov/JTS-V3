package ru.jts_dev.gameserver.packets;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Camelion
 * @since 05.01.16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Opcode {

    /**
     * Alias for {@link #first}.
     *
     * @see #first
     */
    @AliasFor("first") int value() default 0xD0; // switch opcode

    /**
     * first packet opcode (byte)
     *
     * @return - expected opcode
     */
    @AliasFor("value") int first() default 0xD0; // switch opcode

    /**
     * second packet opcode (short)
     *
     * @return - expected opcode
     */
    int second() default Integer.MIN_VALUE;
}
