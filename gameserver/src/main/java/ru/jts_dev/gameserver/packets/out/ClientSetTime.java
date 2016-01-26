package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Java-man
 * @since 03.01.2016
 */
public class ClientSetTime extends OutgoingMessageWrapper {
    private final int minutesPassed;

    public ClientSetTime(final int minutesPassed) {
        this.minutesPassed = minutesPassed;
    }

    @Override
    public void write() {
        writeByte(0xF2);

        writeInt(minutesPassed); // time in client minutes
        writeInt(6); //constant to match the server time (this determines the speed of the client clock)
    }
}
