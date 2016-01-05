package ru.jts_dev.gameserver.parser.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.parser.SettingsBaseListener;
import ru.jts_dev.gameserver.parser.SettingsLexer;
import ru.jts_dev.gameserver.parser.SettingsParser;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 15.12.15
 */
// TODO: 05.01.16 handle initial equipment
@Component
public class SettingsData extends SettingsBaseListener {
    private final Map<String, List<Vector3D>> initialStartPoints = new HashMap<>();
    private final List<CharacterStat> minimumStats = new ArrayList<>();
    private final List<CharacterStat> recommendedStats = new ArrayList<>();
    private final List<CharacterStat> maximumStats = new ArrayList<>();

    @Autowired
    private ApplicationContext context;

    public Map<String, List<Vector3D>> getInitialStartPoints() {
        return Collections.unmodifiableMap(initialStartPoints);
    }

    public List<CharacterStat> getMinimumStats() {
        return Collections.unmodifiableList(minimumStats);
    }

    public List<CharacterStat> getRecommendedStats() {
        return Collections.unmodifiableList(recommendedStats);
    }

    public List<CharacterStat> getMaximumStats() {
        return Collections.unmodifiableList(maximumStats);
    }

    @Override
    public void exitMinimum_stat(SettingsParser.Minimum_statContext ctx) {
        // human
        minimumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_array()));
        minimumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_array()));

        // elf
        minimumStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_array()));
        minimumStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_array()));

        // darkelf
        minimumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_array()));
        minimumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_array()));

        // orc
        minimumStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_array()));
        minimumStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_array()));

        // dwarf
        minimumStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_array()));

        // kamael
        minimumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_array()));
        minimumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_array()));
    }

    @Override
    public void exitMaximum_stat(SettingsParser.Maximum_statContext ctx) {
        // human
        maximumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_array()));
        maximumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_array()));

        // elf
        maximumStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_array()));
        maximumStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_array()));

        // darkelf
        maximumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_array()));
        maximumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_array()));

        // orc
        maximumStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_array()));
        maximumStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_array()));

        // dwarf
        maximumStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_array()));

        // kamael
        maximumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_array()));
        maximumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_array()));
    }

    @Override
    public void exitRecommended_stat(SettingsParser.Recommended_statContext ctx) {
        // human
        recommendedStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_array()));
        recommendedStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_array()));

        // elf
        recommendedStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_array()));
        recommendedStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_array()));

        // darkelf
        recommendedStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_array()));
        recommendedStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_array()));

        // orc
        recommendedStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_array()));
        recommendedStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_array()));

        // dwarf
        recommendedStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_array()));

        // kamael
        recommendedStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_array()));
        recommendedStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_array()));
    }

    @Override
    public void exitInitial_start_point(SettingsParser.Initial_start_pointContext ctx) {
        for (SettingsParser.Start_pointContext sctx : ctx.start_point()) {
            List<Vector3D> points = new ArrayList<>();

            for (SettingsParser.PointContext pctx : sctx.point()) {
                points.add(vectorFromArray(pctx.int_array()));
            }

            String[] classes = identifiersFromArray(sctx.class_().identifier_array());

            for (String class_ : classes) {
                initialStartPoints.put(class_, Collections.unmodifiableList(points));
            }
        }
    }

    /**
     * Converts ANTLR Generated {@link ru.jts_dev.gameserver.parser.SettingsParser.Int_arrayContext}
     * to Java {@code int[]} array
     *
     * @param ctx - parser {@link ru.jts_dev.gameserver.parser.SettingsParser.Int_arrayContext}
     * @return - filled int[] array
     */
    private int[] convertFromAntlrArray(SettingsParser.Int_arrayContext ctx) {
        int[] array = new int[ctx.int_object().size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.valueOf(ctx.int_object(i).getText());
        }

        return array;
    }

    /**
     * Converts ANTLR Generated {@link ru.jts_dev.gameserver.parser.SettingsParser.Int_arrayContext}
     * to {@link Vector3D}
     *
     * @param ctx - parser {@link ru.jts_dev.gameserver.parser.SettingsParser.Int_arrayContext}
     * @return - Vector3D
     */
    private Vector3D vectorFromArray(SettingsParser.Int_arrayContext ctx) {
        int[] array = convertFromAntlrArray(ctx);

        assert array.length == 3;

        return new Vector3D(array[0], array[1], array[2]);
    }

    /**
     * Converts ANTLR Generated {@link ru.jts_dev.gameserver.parser.SettingsParser.Identifier_arrayContext}
     * to Java {@code String[]} array
     *
     * @param ctx - parser {@link ru.jts_dev.gameserver.parser.SettingsParser.Int_arrayContext}
     * @return - Vector3D
     */
    private String[] identifiersFromArray(SettingsParser.Identifier_arrayContext ctx) {
        String[] array = new String[ctx.identifier_object().size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = ctx.identifier_object(i).getText();
        }

        return array;
    }

    private CharacterStat statFrom(int raceId, int classId, String name, SettingsParser.Int_arrayContext ctx) {
        return new CharacterStat(raceId, classId, name, convertFromAntlrArray(ctx));
    }

    @PostConstruct
    public void parse() throws IOException {
        Resource file = context.getResource("scripts/setting.txt");
        try (InputStream is = file.getInputStream()) {
            ANTLRInputStream input = new ANTLRInputStream(is);
            SettingsLexer lexer = new SettingsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SettingsParser parser = new SettingsParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
