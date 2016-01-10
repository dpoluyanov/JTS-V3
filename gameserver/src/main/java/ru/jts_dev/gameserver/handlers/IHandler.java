package ru.jts_dev.gameserver.handlers;

public interface IHandler<K, V> {
    void registerHandler(K handler);

    K getHandler(V val);

    int size();
}