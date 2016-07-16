package ru.jts_dev.common.id;

/**
 * Represents IdPool with fixed id range in [1 ... {@linkplain Integer#MAX_VALUE}]
 *
 * @author Java-man
 * @since 21.01.2016
 */
public interface IdPool {
    int borrow();

    void release(int id);
}
