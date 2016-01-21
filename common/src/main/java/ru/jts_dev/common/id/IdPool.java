package ru.jts_dev.common.id;

/**
 * @author Java-man
 * @since 21.01.2016
 */
public interface IdPool {
    int borrow();

    void release(int objectId);
}
