package ru.jts_dev.gameserver.packets.in;

import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Opcode(second = 0x3D)
public class RequestShortCutReg extends IncomingMessageWrapper {
    @Override
    public void prepare() {
        // TODO: 26.01.16 check data structure
    }

    @Override
    public void run() {
        // TODO: 03.01.16
        throw new UnsupportedOperationException("Not released yet");
    }
}
