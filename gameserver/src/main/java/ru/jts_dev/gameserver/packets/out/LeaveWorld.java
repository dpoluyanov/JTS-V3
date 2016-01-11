package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class LeaveWorld extends OutgoingMessageWrapper {
    public static final LeaveWorld PACKET = new LeaveWorld();

    private LeaveWorld() {
    }

    @Override
    public void write() {
        putByte(0x84);
    }
}
