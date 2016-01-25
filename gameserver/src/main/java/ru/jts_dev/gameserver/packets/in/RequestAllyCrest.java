package ru.jts_dev.gameserver.packets.in;

import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Opcode(0x92)
public class RequestAllyCrest extends IncomingMessageWrapper {
    private int crestId;

    @Override
    public void prepare() {
        crestId = readInt();
    }

    @Override
    public void run() {
        // TODO: 06.01.16 send crest id
        throw new UnsupportedOperationException("Not released yet");
    }
}
