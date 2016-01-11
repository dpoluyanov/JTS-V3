package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Java-man
 * @since 12.01.2016
 */
public class ActionFailed extends OutgoingMessageWrapper {
    public static final ActionFailed PACKET = new ActionFailed();

    private ActionFailed() {
    }

    @Override
    public void write() {
        putByte(0x1F);
    }
}