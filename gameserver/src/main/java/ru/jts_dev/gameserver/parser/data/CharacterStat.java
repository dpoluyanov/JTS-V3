package ru.jts_dev.gameserver.parser.data;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

/**
 * @author Camelion
 * @since 18.12.15
 */
@Embeddable
public class CharacterStat implements Cloneable {
    // races
    public static final int RACE_HUMAN = 0;
    public static final int RACE_ELF = 1;
    public static final int RACE_DARKELF = 2;
    public static final int RACE_ORC = 3;
    public static final int RACE_DWARF = 4;
    public static final int RACE_KAMAEL = 5;

    public static final String HUMAN_FIGHTER = "human_fighter";
    public static final String HUMAN_MAGICIAN = "human_magician";
    public static final String ELF_FIGHTER = "elf_fighter";
    public static final String ELF_MAGICIAN = "elf_magician";
    public static final String DARKELF_FIGHTER = "darkelf_fighter";
    public static final String DARKELF_MAGICIAN = "darkelf_magician";
    public static final String ORC_FIGHTER = "orc_fighter";
    public static final String ORC_SHAMAN = "orc_shaman";
    public static final String DWARF_APPRENTICE = "dwarf_apprentice";
    public static final String KAMAEL_M_SOLDIER = "kamael_m_soldier";
    public static final String KAMAEL_F_SOLDIER = "kamael_f_soldier";

    // classes
    // human
    public static final int CLASS_HUMAN_FIGHTER = 0;
    public static final int CLASS_HUMAN_MAGICIAN = 10;

    // elf
    public static final int CLASS_ELF_FIGHTER = 18;
    public static final int CLASS_ELF_MAGICIAN = 25;

    // darkelf
    public static final int CLASS_DARKELF_FIGHTER = 31;
    public static final int CLASS_DARKELF_MAGICIAN = 38;

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

    @Column
    private String statName;

    @ElementCollection
    // INT, STR, CON, MEN, DEX, WIT
    private List<Integer> stats;

    @Range(min = RACE_HUMAN, max = RACE_KAMAEL)
    @Column
    private int raceId;

    // TODO: 05.01.16 move to our validator
    /*
    @Digits.List({
            @Digits(integer = CLASS_HUMAN_FIGHTER, fraction = 0, message = ""),
            @Digits(integer = CLASS_HUMAN_MAGICIAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_MAGICIAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_MAGICIAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_SHAMAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DWARF_APPRENTICE, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
    })*/
    @Column
    private int classId;

    public CharacterStat(int raceId, int classId, String statName, List<Integer > stats) {
        this.raceId = raceId;
        this.classId = classId;
        this.stats = stats;
        this.statName = statName;
    }

    // only for jpa
    private CharacterStat() {
    }

    public int getRaceId() {
        return raceId;
    }

    public int getClassId() {
        return classId;
    }

    public String getStatName() {
        return statName;
    }

    public int getForType(int type) {
        if (type >= stats.size())
            throw new IndexOutOfBoundsException("Unknown stat type: " + type + " possible 0-5, INT, STR, CON, MEN, DEX, WIT");

        return stats.get(type);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
}
