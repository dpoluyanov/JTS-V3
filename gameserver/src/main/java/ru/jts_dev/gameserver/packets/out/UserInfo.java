package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;
import static ru.jts_dev.gameserver.parser.impl.PCParametersData.HEIGHT;
import static ru.jts_dev.gameserver.parser.impl.PCParametersData.RADIUS;

/**
 * @author Camelion
 * @since 03.01.16
 */
public class UserInfo extends OutgoingMessageWrapper {

    private final GameCharacter character;
    private final double[] collisions;

    public UserInfo(GameCharacter character, double[] collisions) {
        this.character = character;
        this.collisions = collisions;
    }

    @Override
    public void write() {
        putByte(0x32);

        putInt((int) character.getVector3D().getX());
        putInt((int) character.getVector3D().getY());
        putInt((int) character.getVector3D().getZ());
        putInt(0); // Vehicle Object Id
        putInt(character.getObjectId());
        putString(character.getName());
        putInt(character.getStat().getRaceId());
        putInt(character.getSex());

        putInt(character.getStat().getClassId());

        putInt(character.getLevel());
        putLong(character.getExp());
        putInt(character.getStat().getForType(STR)); // str
        putInt(character.getStat().getForType(DEX)); // dex
        putInt(character.getStat().getForType(CON)); // con
        putInt(character.getStat().getForType(INT)); // int
        putInt(character.getStat().getForType(WIT)); // wit
        putInt(character.getStat().getForType(MEN)); // men
        putInt((int) character.getMaxHp());
        putInt((int) character.getHp());
        putInt((int) character.getMaxMp());
        putInt((int) character.getMp());
        putInt(character.getSp());
        putInt(0x00); // current load
        putInt(0x00); // max load

        putInt(20); // active weapon: 20 no weapon, 40 weapon equippe

        // Object ids
        putInt(0); // under
        putInt(0); // read
        putInt(0); // lear
        putInt(0); // neck
        putInt(0); // rfinger
        putInt(0); // lfinger
        putInt(0); // head
        putInt(0); // rhand
        putInt(0); // lhand
        putInt(0); // gloves
        putInt(0); // chest
        putInt(0); // legs
        putInt(0); // feet
        putInt(0); // cloak
        putInt(0); // rhand
        putInt(0); // hair
        putInt(0); // hair2
        putInt(0); // rbracelet
        putInt(0); // lbracelet
        putInt(0); // deco1
        putInt(0); // deco2
        putInt(0); // deco3
        putInt(0); // deco4
        putInt(0); // deco5
        putInt(0); // deco6
        putInt(0); // belt

        // Item ids
        putInt(0); // under
        putInt(0); // rear
        putInt(0); // lear
        putInt(0); // neck
        putInt(0); // rfinger
        putInt(0); // lfinger
        putInt(0); // head
        putInt(0); // rhand
        putInt(0); // lhand
        putInt(0); // gloves
        putInt(0); // chest
        putInt(0); // legs
        putInt(0); // feet
        putInt(0); // cloack
        putInt(0); // rhand
        putInt(0); // hair
        putInt(0); // hair2
        putInt(0); // rbracelet
        putInt(0); // lbracelet
        putInt(0); // deco1
        putInt(0); // deco2
        putInt(0); // deco3
        putInt(0); // deco4
        putInt(0); // deco5
        putInt(0); // deco6
        putInt(0); // belt

        // Augmentation ids
        putInt(0); // under
        putInt(0); // rear
        putInt(0); // lear
        putInt(0); // neck
        putInt(0); // rfinger
        putInt(0); // lfinger
        putInt(0); // head
        putInt(0); // rhand
        putInt(0); // lhand
        putInt(0); // gloves
        putInt(0); // chest
        putInt(0); // legs
        putInt(0); // feet
        putInt(0); // cloak
        putInt(0); // rhand
        putInt(0); // hair
        putInt(0); // hair2
        putInt(0); // rbracelet
        putInt(0); // lbracelet
        putInt(0); // deco1
        putInt(0); // deco2
        putInt(0); // deco3
        putInt(0); // deco4
        putInt(0); // deco5
        putInt(0); // deco6
        putInt(0); // belt

        putInt(0); // max talisman count
        putInt(0); // cloak status
        putInt(0); // patk
        putInt(0); // patkspd
        putInt(0); // pdef
        putInt(0); // evasion rate
        putInt(0); // accuracy
        putInt(0); // critical hit
        putInt(0); // matk

        putInt(0); // matk spd
        putInt(0); // patk spd

        putInt(0); // mdef

        putInt(0); // pvp flag: 0-non-pvp 1-pvp = violett name
        putInt(0); // karma

        putInt(0); // run speed
        putInt(0); //walk speed
        putInt(0); // swim run speed
        putInt(0); // swim walk speed
        putInt(0);
        putInt(0);
        putInt(0); // fly speed
        putInt(0); // fly speed
        putDouble(0); // move multiplier
        putDouble(0); // attack speed multiplier

        putDouble(collisions[RADIUS]); // collision radius
        putDouble(collisions[HEIGHT]); // collision height

        putInt(character.getHairStyle());
        putInt(character.getHairColor());
        putInt(character.getFace()); //
        putInt(0); // is gm: 1 - true, 0 - false (builder level)

        putString(""); // titile

        putInt(0); // clanId
        putInt(0); // clan crest id
        putInt(0); // ally id
        putInt(0); // ally crest id
        // 0x40 leader rights
        // siege flags: attacker - 0x180 sword over name, defender - 0x80 shield, 0xC0 crown (|leader), 0x1C0 flag (|leader)
        putInt(0);
        putByte(0); // mount type
        putByte(0); // private store type
        putByte(0); // has dwarven craft
        putInt(0); // pk kills
        putInt(0); // pvp kills

        putShort(0); // cubics size
        //for (int id : character.getCubics().keySet()) {
        //    putShort(id);
        //}

        putByte(0); // is in party match room

        putInt(0); // abnormal effect
        putByte(0); // flying mounted 2 - true, 0 - false

        putInt(0); // clan privilegies

        putShort(0); // c2 recommendations remaining
        putShort(0); // c2 recommendations received
        putInt(0); // mount npcid
        putShort(0); // inverntory limit

        putInt(character.getStat().getClassId());
        putInt(0x00); // special effects? circles around player...
        putInt(0); // max CP
        putInt(0); // current CP
        putByte(0); // enchant effect

        putByte(0); // team circle around feet 1= Blue, 2 = red

        putInt(0); // clan crest large id
        putByte(0); // 0x01: symbol on char menu ctrl+I is noble ? 1 : 0
        putByte(0); // 0x01: Hero Aura 0x00 - nothing

        putByte(0); // Fishing Mode 1 - true, 0 - false
        putInt(0); // fishing x
        putInt(0); // fishing y
        putInt(0); // fishing z
        putInt(0xFFFFFF); // name color

        // new c5
        putByte(0); // running ? 0x01 : 0x00 changes the Speed display on Status Window

        putInt(0); // pledge class changes the text above CP on Status Window
        putInt(0); // pledge type

        putInt(0xFFFF77); // title color

        putInt(0); // curse weapon level

        // T1 Starts
        putInt(0); // transformation id


        putShort(-2); // attack attribute
        putShort(0); // attack value
        putShort(0); // fire def
        putShort(0); // wter def
        putShort(0); // wind def
        putShort(0); // earth def
        putShort(0); // holy def
        putShort(0); // dark def

        putInt(0); // agathion id

        // T2 Starts
        putInt(0); // Fame
        putInt(0); // minimap allowed? on hellbound, for example
        putInt(0); // Vitality Points
        putInt(0); // special effects
    }
}
