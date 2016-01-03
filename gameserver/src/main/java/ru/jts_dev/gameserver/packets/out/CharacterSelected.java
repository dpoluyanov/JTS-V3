package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Camelion
 * @since 03.01.16
 */
public class CharacterSelected extends OutgoingMessageWrapper {
    private GameCharacter character;
    private int playKey;

    public CharacterSelected(GameCharacter character, int playKey) {
        this.character = character;
        this.playKey = playKey;
    }

    @Override
    public void write() {
        putByte(0x0B);

        putString(character.getName());
        putInt(character.getObjectId());
        putString(""); // title
        putInt(playKey);
        putInt(0); // clanId
        putInt(0x00); // ??
        putInt(character.getSex());
        putInt(character.getRaceId());
        putInt(character.getClassId());
        putInt(0x01); // active ??
        putInt((int) character.getVector3D().getX());
        putInt((int) character.getVector3D().getY());
        putInt((int) character.getVector3D().getZ());

        putDouble(0x00); // hp
        putDouble(0x00); // mp
        putInt(character.getSp());
        putLong(character.getExp());
        putInt(character.getLevel());
        putInt(0x00); // karma
        putInt(0x00);
        putInt(0x00); // INT
        putInt(0x00); // STR
        putInt(0x00); // CON
        putInt(0x00); // MEN
        putInt(0x00); // DEX
        putInt(0x00); // WIT

        putInt(0x00); // Game Time
        putInt(0x00);

        putInt(character.getClassId());

        putInt(0x00);
        putInt(0x00);
        putInt(0x00);
        putInt(0x00);

        putBytes(new byte[64]);
        putInt(0x00);
    }
}
