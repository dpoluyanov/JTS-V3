package ru.jts_dev.common;

import java.util.function.Function;

/**
 * Class for exception functions.
 *
 * @author Camelion
 * @since 27.01.16
 */
public class Exceptions {
    /**
     * Wraps any unchecked exception
     *
     * @param <T>
     */
    @FunctionalInterface
    public interface ThrowingFunction<T, R> extends Function<T, R> {

        @Override
        default R apply(T t) throws RuntimeException {
            try {
                return applyThrows(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        R applyThrows(T t) throws Exception;
    }
}
