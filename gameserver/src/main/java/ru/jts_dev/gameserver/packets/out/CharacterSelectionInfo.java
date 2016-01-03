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
        putByte(0x09);
        putInt(characters.size());

        putInt(MAX_CHARACTERS_CREATE_SIZE);
        putByte(charCreationDisabled ? 0x01 : 0x00); // enable character creation restriction?
        for (GameCharacter character : characters) {
            putString(character.getName()); // Name
            putInt(character.getObjectId()); // Char ID(objectID)
            putString(character.getLogin()); // Login account name
            putInt(playKey); // Session ID
            putInt(0x00); // Clan ID
            putInt(0x00); // ??

            putInt(character.getSex()); // Sex
            putInt(character.getRaceId()); // Race

            putInt(character.getClassId()); // Class ID

            putInt(0x01); // activate char? (0x00 not active)

            putInt((int) character.getVector3D().getX()); // x
            putInt((int) character.getVector3D().getY()); // y
            putInt((int) character.getVector3D().getZ()); // z

            putDouble(character.getHp()); // hp cur
            putDouble(character.getMp()); // mp cur

            putInt(character.getSp()); // SP
            putLong(character.getExp()); // EXP
            putInt(character.getLevel()); // LEVEL

            putInt(0x00); // karma
            putInt(0x00); // PK Kills
            putInt(0x00); // PVP Kills

            putInt(0x00);
            putInt(0x00);
            putInt(0x00);
            putInt(0x00);
            putInt(0x00);
            putInt(0x00);
            putInt(0x00);

            putInt(0x00); // Hair
            putInt(0x00); // REarning
            putInt(0x00); // LEarning slot
            putInt(0x00); // Necklacke
            putInt(0x00); // RFinger
            putInt(0x00); // LFinger
            putInt(0x00); // Head
            putInt(0x00); // RHand
            putInt(0x00); // LHand
            putInt(0x00); // Gloves
            putInt(0x00); // Chest
            putInt(0x00); // Legs
            putInt(0x00); // Feet
            putInt(0x00); // Back
            putInt(0x00); // LRHand
            putInt(0x00); // Hair
            putInt(0x00); // Hair 2

            putInt(0x00); // Bracelet
            putInt(0x00); // Bracelet
            putInt(0x00); // Deco 1
            putInt(0x00); // Deco 2
            putInt(0x00); // Deco 3
            putInt(0x00); // Deco 4
            putInt(0x00); // Deco 5
            putInt(0x00); // Deco 6
            putInt(0x00); // Belt

            putInt(character.getHairStyle()); // Hair style
            putInt(character.getHairColor()); // Hair color
            putInt(character.getFace()); // Face

            putDouble(character.getMaxHP()); // hp max
            putDouble(0.0); // mp max

            putInt(0x00); // days left before

            putInt(character.getClassId()); // Class Id

            putInt(character.isLastUsed() ? 0x01 : 0x00); //c3 auto-select char (0x01 - this char is active)

            putByte(0x00); // EnchantEffect

            putShort(0x00); // Augmentation Id
            putShort(0x00); // this is for augmentation too

            putInt(0x00); // Transformation ID

            putInt(0); // npdid - 16024 Tame Tiny Baby Kookaburra A9E89C
            putInt(0); // level
            putInt(0); // ?
            putInt(0); // food? - 1200
            putInt(0); // max Hp
            putInt(0); // cur Hp
        }
    }
}
