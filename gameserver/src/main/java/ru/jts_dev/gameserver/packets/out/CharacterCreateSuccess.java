package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class CharacterCreateSuccess extends OutgoingMessageWrapper {
    @Override
    public void write() {
        putByte(0x0F);
        putInt(0x01);
    }
}
