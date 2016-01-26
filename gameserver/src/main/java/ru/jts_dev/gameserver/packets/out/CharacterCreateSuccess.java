package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class CharacterCreateSuccess extends OutgoingMessageWrapper {
    public static final CharacterCreateSuccess PACKET = new CharacterCreateSuccess();

    private CharacterCreateSuccess() {
    }

    @Override
    public void write() {
        writeByte(0x0F);

        writeInt(0x01);
    }
}
