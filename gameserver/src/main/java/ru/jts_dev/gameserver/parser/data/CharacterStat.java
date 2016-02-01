package ru.jts_dev.gameserver.parser.data;

import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.constants.CharacterRace;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Camelion
 * @since 18.12.15
 */
@Embeddable
public class CharacterStat implements Cloneable {
    public static final int INT = 0;
    public static final int STR = 1;
    public static final int CON = 2;
    public static final int MEN = 3;
    public static final int DEX = 4;
    public static final int WIT = 5;

    @NotNull
    @Column
    private CharacterRace race;

    @NotNull
    @Column(name = "class")
    private CharacterClass class_;

    @ElementCollection(fetch = EAGER)
    // INT, STR, CON, MEN, DEX, WIT
    private List<Integer> stats;

    public CharacterStat(final CharacterRace race, final CharacterClass class_, final List<Integer> stats) {
        this.race = race;
        this.class_ = class_;
        this.stats = stats;
    }

    // only for jpa
    private CharacterStat() {
    }

    public final int getForType(final int type) {
        assert type >= 0 && type < stats.size() : "Unknown stat type: " + type + " possible 0-5, INT, STR, CON, MEN, DEX, WIT";

        return stats.get(type);
    }

    @Override
    public final Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    public final CharacterRace getRace() {
        return race;
    }

    public final CharacterClass getClass_() {
        return class_;
    }
}
