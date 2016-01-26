package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.StaticOutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class CharacterCreateSuccess extends StaticOutgoingMessageWrapper {
    public static final CharacterCreateSuccess PACKET = new CharacterCreateSuccess();

    @Override
    public void write() {
        writeByte(0x0F);

        writeInt(0x01);
    }
}
