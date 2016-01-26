package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.List;

/**
 * @author Camelion
 * @since 13.12.15
 */
public class CharacterSelectionInfo extends OutgoingMessageWrapper {
    private static final int MAX_CHARACTERS_CREATE_SIZE = 7;
    private final List<GameCharacter> characters;
    private final int playKey;
    private final boolean charCreationDisabled;

    public CharacterSelectionInfo(List<GameCharacter> characters, int playKey, boolean charCreationDisabled) {
        this.characters = characters;
        this.playKey = playKey;
        this.charCreationDisabled = charCreationDisabled;
    }

    @Override
    public void write() {
        writeByte(0x09);
        writeInt(characters.size());

        writeInt(MAX_CHARACTERS_CREATE_SIZE);
        writeByte(charCreationDisabled ? 0x01 : 0x00); // enable character creation restriction?
        for (GameCharacter character : characters) {
            writeString(character.getName()); // Name
            writeInt(character.getObjectId()); // Char ID(objectID)
            writeString(character.getLogin()); // Login account name
            writeInt(playKey); // Session ID
            writeInt(0x00); // Clan ID
            writeInt(0x00); // ??

            writeInt(character.getSex()); // Sex
            writeInt(character.getStat().getRace().getId()); // Race

            writeInt(character.getStat().getClass_().getId()); // Class ID

            writeInt(0x01); // activate char? (0x00 not active)

            writeInt((int) character.getVector3D().getX()); // x
            writeInt((int) character.getVector3D().getY()); // y
            writeInt((int) character.getVector3D().getZ()); // z

            writeDouble(character.getHp()); // hp cur
            writeDouble(character.getMp()); // mp cur

            writeInt(character.getSp()); // SP
            writeLong(character.getExp()); // EXP
            writeDouble(0x00); // TODO: 26.01.16 exp percent
            writeInt(character.getLevel());

            writeInt(0x00); // karma
            writeInt(0x00); // PK Kills
            writeInt(0x00); // PVP Kills

            writeInt(0x00);
            writeInt(0x00);
            writeInt(0x00);
            writeInt(0x00);
            writeInt(0x00);
            writeInt(0x00);
            writeInt(0x00);

            writeInt(0x00); // Hair
            writeInt(0x00); // REarning
            writeInt(0x00); // LEarning slot
            writeInt(0x00); // Necklacke
            writeInt(0x00); // RFinger
            writeInt(0x00); // LFinger
            writeInt(0x00); // Head
            writeInt(0x00); // RHand
            writeInt(0x00); // LHand
            writeInt(0x00); // Gloves
            writeInt(0x00); // Chest
            writeInt(0x00); // Legs
            writeInt(0x00); // Feet
            writeInt(0x00); // Back
            writeInt(0x00); // LRHand
            writeInt(0x00); // Hair
            writeInt(0x00); // Hair 2

            writeInt(0x00); // Bracelet
            writeInt(0x00); // Bracelet
            writeInt(0x00); // Deco 1
            writeInt(0x00); // Deco 2
            writeInt(0x00); // Deco 3
            writeInt(0x00); // Deco 4
            writeInt(0x00); // Deco 5
            writeInt(0x00); // Deco 6
            writeInt(0x00); // Belt

            writeInt(character.getHairStyle()); // Hair style
            writeInt(character.getHairColor()); // Hair color
            writeInt(character.getFace()); // Face

            writeDouble(character.getMaxHp()); // hp max
            writeDouble(character.getMaxMp()); // mp max

            writeInt(0x00); // days left before

            writeInt(character.getStat().getClass_().getId()); // Class Id

            writeInt(character.isLastUsed() ? 0x01 : 0x00); //c3 auto-select char (0x01 - this char is active)

            writeByte(0x00); // EnchantEffect

            writeShort(0x00); // Augmentation Id
            writeShort(0x00); // this is for augmentation too

            writeInt(0x00); // Transformation ID

            writeInt(0); // npdid - 16024 Tame Tiny Baby Kookaburra A9E89C
            writeInt(0); // level
            writeInt(0); // ?
            writeInt(0); // food? - 1200
            writeInt(0); // max Hp
            writeInt(0); // cur Hp

            writeInt(0x00); // vitality points
        }
    }
}
