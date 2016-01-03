package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Java-man
 * @since 03.01.2016
 */
public class ClientSetTime extends OutgoingMessageWrapper {
    private final long gameTimeInMinutes;

    public ClientSetTime(long gameTimeInMinutes) {
        this.gameTimeInMinutes = gameTimeInMinutes;
    }

    @Override
    public void write() {
        putInt((int) gameTimeInMinutes); // time in client minutes
        putInt(6); //constant to match the server time (this determines the speed of the client clock)
    }
}
