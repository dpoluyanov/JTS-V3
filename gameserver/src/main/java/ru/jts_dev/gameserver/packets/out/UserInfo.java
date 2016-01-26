package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.List;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;
import static ru.jts_dev.gameserver.parser.impl.PcParametersHolder.HEIGHT;
import static ru.jts_dev.gameserver.parser.impl.PcParametersHolder.RADIUS;

/**
 * @author Camelion
 * @since 03.01.16
 */
public class UserInfo extends OutgoingMessageWrapper {

    private final GameCharacter character;
    private final List<Double> collisions;

    public UserInfo(final GameCharacter character, final List<Double> collisions) {
        this.character = character;
        this.collisions = collisions;
    }

    @Override
    public void write() {
        writeByte(0x32);

        writeInt((int) character.getVector3D().getX());
        writeInt((int) character.getVector3D().getY());
        writeInt((int) character.getVector3D().getZ());
        writeInt(0); // Vehicle Object Id
        writeInt(character.getObjectId());
        writeString(character.getName());
        writeInt(character.getStat().getRace().getId());
        writeInt(character.getSex());

        writeInt(character.getStat().getClass_().getId());

        writeInt(character.getLevel());
        writeLong(character.getExp());
        writeDouble(0x00); // TODO: 26.01.16 EXP percent
        writeInt(character.getStat().getForType(STR)); // str
        writeInt(character.getStat().getForType(DEX)); // dex
        writeInt(character.getStat().getForType(CON)); // con
        writeInt(character.getStat().getForType(INT)); // int
        writeInt(character.getStat().getForType(WIT)); // wit
        writeInt(character.getStat().getForType(MEN)); // men
        writeInt((int) character.getMaxHp());
        writeInt((int) character.getHp());
        writeInt((int) character.getMaxMp());
        writeInt((int) character.getMp());
        writeInt(character.getSp());
        writeInt(0x00); // current load
        writeInt(0x00); // max load

        writeInt(20); // active weapon: 20 no weapon, 40 weapon equippe

        // Object ids
        writeInt(0); // under
        writeInt(0); // read
        writeInt(0); // lear
        writeInt(0); // neck
        writeInt(0); // rfinger
        writeInt(0); // lfinger
        writeInt(0); // head
        writeInt(0); // rhand
        writeInt(0); // lhand
        writeInt(0); // gloves
        writeInt(0); // chest
        writeInt(0); // legs
        writeInt(0); // feet
        writeInt(0); // cloak
        writeInt(0); // rhand
        writeInt(0); // hair
        writeInt(0); // hair2
        writeInt(0); // rbracelet
        writeInt(0); // lbracelet
        writeInt(0); // deco1
        writeInt(0); // deco2
        writeInt(0); // deco3
        writeInt(0); // deco4
        writeInt(0); // deco5
        writeInt(0); // deco6
        writeInt(0); // belt

        // Item ids
        writeInt(0); // under
        writeInt(0); // rear
        writeInt(0); // lear
        writeInt(0); // neck
        writeInt(0); // rfinger
        writeInt(0); // lfinger
        writeInt(0); // head
        writeInt(0); // rhand
        writeInt(0); // lhand
        writeInt(0); // gloves
        writeInt(0); // chest
        writeInt(0); // legs
        writeInt(0); // feet
        writeInt(0); // cloack
        writeInt(0); // rhand
        writeInt(0); // hair
        writeInt(0); // hair2
        writeInt(0); // rbracelet
        writeInt(0); // lbracelet
        writeInt(0); // deco1
        writeInt(0); // deco2
        writeInt(0); // deco3
        writeInt(0); // deco4
        writeInt(0); // deco5
        writeInt(0); // deco6
        writeInt(0); // belt

        // Augmentation ids
        writeInt(0); // under
        writeInt(0); // rear
        writeInt(0); // lear
        writeInt(0); // neck
        writeInt(0); // rfinger
        writeInt(0); // lfinger
        writeInt(0); // head
        writeInt(0); // rhand
        writeInt(0); // lhand
        writeInt(0); // gloves
        writeInt(0); // chest
        writeInt(0); // legs
        writeInt(0); // feet
        writeInt(0); // cloak
        writeInt(0); // rhand
        writeInt(0); // hair
        writeInt(0); // hair2
        writeInt(0); // rbracelet
        writeInt(0); // lbracelet
        writeInt(0); // deco1
        writeInt(0); // deco2
        writeInt(0); // deco3
        writeInt(0); // deco4
        writeInt(0); // deco5
        writeInt(0); // deco6
        writeInt(0); // belt

        writeInt(0); // max talisman count
        writeInt(0); // cloak status
        writeInt(0); // patk
        writeInt(0); // patkspd
        writeInt(0); // pdef
        writeInt(0); // evasion rate
        writeInt(0); // accuracy
        writeInt(0); // critical hit
        writeInt(0); // matk

        writeInt(0); // matk spd
        writeInt(0); // patk spd

        writeInt(0); // mdef

        writeInt(0); // pvp flag: 0-non-pvp 1-pvp = violett name
        writeInt(0); // karma

        writeInt(100); // run speed
        writeInt(100); // walk speed
        writeInt(0); // swim run speed
        writeInt(0); // swim walk speed
        writeInt(0);
        writeInt(0);
        writeInt(0); // fly speed
        writeInt(0); // fly speed
        writeDouble(1); // move multiplier
        writeDouble(0); // attack speed multiplier

        writeDouble(collisions.get(RADIUS)); // collision radius
        writeDouble(collisions.get(HEIGHT)); // collision height

        writeInt(character.getHairStyle());
        writeInt(character.getHairColor());
        writeInt(character.getFace()); //
        writeInt(0); // is gm: 1 - true, 0 - false (builder level)

        writeString(""); // titile

        writeInt(0); // clanId
        writeInt(0); // clan crest id
        writeInt(0); // ally id
        writeInt(0); // ally crest id
        // 0x40 leader rights
        // siege flags: attacker - 0x180 sword over name, defender - 0x80 shield, 0xC0 crown (|leader), 0x1C0 flag (|leader)
        writeInt(0);
        writeByte(0); // mount type
        writeByte(0); // private store type
        writeByte(0); // has dwarven craft
        writeInt(0); // pk kills
        writeInt(0); // pvp kills

        writeShort(0); // cubics size
        //for (int id : character.getCubics().keySet()) {
        //    writeShort(id);
        //}

        writeByte(0); // is in party match room

        writeInt(0); // abnormal effect
        writeByte(0); // in fly  - 2, in water - 1, normal - 1

        writeInt(0); // clan privilegies

        writeShort(0); // c2 recommendations remaining
        writeShort(0); // c2 recommendations received
        writeInt(0); // mount npcid
        writeShort(0); // inverntory limit

        writeInt(character.getStat().getClass_().getId());
        writeInt(0x00); // special effects? circles around player...
        writeInt(0); // max CP
        writeInt(0); // current CP
        writeByte(0); // enchant effect

        writeByte(0); // team circle around feet 1= Blue, 2 = red

        writeInt(0); // clan crest large id
        writeByte(0); // 0x01: symbol on char menu ctrl+I is noble ? 1 : 0
        writeByte(0); // 0x01: Hero Aura 0x00 - nothing

        writeByte(0); // Fishing Mode 1 - true, 0 - false
        writeInt(0); // fishing x
        writeInt(0); // fishing y
        writeInt(0); // fishing z

        writeInt(0xFFFFFF); // name color

        writeByte(0x01); // running ? 0x01 : 0x00 changes the Speed display on Status Window

        writeInt(0); // pledge class, changes the text above CP on Status Window
        writeInt(0); // pledge type

        writeInt(0xFFFF77); // title color

        writeInt(0); // curse weapon level

        writeInt(0); // transformation id


        writeShort(-2); // attack attribute
        writeShort(0); // attack value
        writeShort(0); // fire def
        writeShort(0); // wter def
        writeShort(0); // wind def
        writeShort(0); // earth def
        writeShort(0); // holy def
        writeShort(0); // dark def

        writeInt(0); // agathion id

        writeInt(0); // Fame
        writeInt(0); // minimap allowed? on hellbound, for example
        writeInt(0); // Vitality Points
        writeInt(0); // special effects
    }
}
