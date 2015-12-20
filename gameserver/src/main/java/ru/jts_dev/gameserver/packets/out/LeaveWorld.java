package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class LeaveWorld extends OutgoingMessageWrapper {
    @Override
    public void write() {
        putByte(0x84);
    }
}
