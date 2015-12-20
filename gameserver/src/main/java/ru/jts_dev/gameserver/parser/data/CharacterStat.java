package ru.jts_dev.gameserver.parser.data;

/**
 * @author Camelion
 * @since 18.12.15
 */
public class CharacterStat {
    // races
    public static final int RACE_HUMAN = 0;
    public static final int RACE_ELF = 1;
    public static final int RACE_DARKELF = 2;
    public static final int RACE_ORC = 3;
    public static final int RACE_DWARF = 4;
    public static final int RACE_KAMAEL = 5;

    // classes
    // human
    public static final int CLASS_HUMAN_FIGHTER = 0;
    public static final int CLASS_HUMAN_MAGICAN = 10;

    // elf
    public static final int CLASS_ELF_FIGHTER = 18;
    public static final int CLASS_ELF_MAGICAN = 25;

    // darkelf
    public static final int CLASS_DARKELF_FIGHTER = 31;
    public static final int CLASS_DARKELF_MAGICAN = 38;

    // orc
    public static final int CLASS_ORC_FIGHTER = 44;
    public static final int CLASS_ORC_SHAMAN = 49;

    // dwarf
    public static final int CLASS_DWARF_APPRENTICE = 53;

    // kamael
    public static final int CLASS_KAMAEL_M_SOLDIER = 123;
    public static final int CLASS_KAMAEL_F_SOLDIER = 124;

    public static final int INT = 0;
    public static final int STR = 1;
    public static final int CON = 2;
    public static final int MEN = 3;
    public static final int DEX = 4;
    public static final int WIT = 5;

    private final int raceId;
    private final int classId;

    // INT, STR, CON, MEN, DEX, WIT
    private final int[] stats;

    public CharacterStat(int raceId, int classId, int[] stats) {
        this.raceId = raceId;
        this.classId = classId;
        this.stats = stats;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getClassId() {
        return classId;
    }

    public int getStatForType(int type) {
        if (type >= stats.length)
            throw new IndexOutOfBoundsException("Unknown stat type: " + type + " possible 0-5, INT, STR, CON, MEN, DEX, WIT");

        return stats[type];
    }
}
