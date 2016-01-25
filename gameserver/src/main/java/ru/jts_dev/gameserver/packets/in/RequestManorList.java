package ru.jts_dev.gameserver.packets.in;

import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Opcode(second = 0x01)
public class RequestManorList extends IncomingMessageWrapper {
    @Override
    public void prepare() {

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not released yet");
    }
}
