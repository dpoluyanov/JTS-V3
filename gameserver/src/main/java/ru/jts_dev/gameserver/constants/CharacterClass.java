package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 17.01.16
 */
public enum CharacterClass {
    HUMAN_FIGHTER(0),
    HUMAN_MAGICIAN(10),
    ELF_FIGHTER(18),
    ELF_MAGICIAN(25),
    DARKELF_FIGHTER(31),
    DARKELF_MAGICIAN(38),
    ORC_FIGHTER(44),
    ORC_SHAMAN(49),
    DWARF_APPRENTICE(53),
    KAMAEL_M_SOLDIER(123),
    KAMAEL_F_SOLDIER(124);

    private final int classId;

    CharacterClass(int classId) {
        this.classId = classId;
    }

    public int getId() {
        return classId;
    }
}
