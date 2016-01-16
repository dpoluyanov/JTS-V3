package ru.jts_dev.gameserver.parser.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 15.12.15
 */
// TODO: 05.01.16 handle initial equipment
@Component
public class SettingsHolder extends SettingsBaseListener {
    private static final Logger log = LoggerFactory.getLogger(SettingsHolder.class);
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
        minimumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_list().value));
        minimumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_list().value));

        // elf
        minimumStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_list().value));
        minimumStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_list().value));

        // darkelf
        minimumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_list().value));
        minimumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_list().value));

        // orc
        minimumStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_list().value));
        minimumStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_list().value));

        // dwarf
        minimumStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_list().value));

        // kamael
        minimumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_list().value));
        minimumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_list().value));
    }

    @Override
    public void exitMaximum_stat(SettingsParser.Maximum_statContext ctx) {
        // human
        maximumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_list().value));
        maximumStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_list().value));

        // elf
        maximumStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_list().value));
        maximumStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_list().value));

        // darkelf
        maximumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_list().value));
        maximumStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_list().value));

        // orc
        maximumStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_list().value));
        maximumStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_list().value));

        // dwarf
        maximumStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_list().value));

        // kamael
        maximumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_list().value));
        maximumStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_list().value));
    }

    @Override
    public void exitRecommended_stat(SettingsParser.Recommended_statContext ctx) {
        // human
        recommendedStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_FIGHTER, HUMAN_FIGHTER, ctx.human_fighter_stat().int_list().value));
        recommendedStats.add(statFrom(RACE_HUMAN, CLASS_HUMAN_MAGICIAN, HUMAN_MAGICIAN, ctx.human_magician_stat().int_list().value));

        // elf
        recommendedStats.add(statFrom(RACE_ELF, CLASS_ELF_FIGHTER, ELF_FIGHTER, ctx.elf_fighter_stat().int_list().value));
        recommendedStats.add(statFrom(RACE_ELF, CLASS_ELF_MAGICIAN, ELF_MAGICIAN, ctx.elf_magician_stat().int_list().value));

        // darkelf
        recommendedStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_FIGHTER, DARKELF_FIGHTER, ctx.darkelf_fighter_stat().int_list().value));
        recommendedStats.add(statFrom(RACE_DARKELF, CLASS_DARKELF_MAGICIAN, DARKELF_MAGICIAN, ctx.darkelf_magician_stat().int_list().value));

        // orc
        recommendedStats.add(statFrom(RACE_ORC, CLASS_ORC_FIGHTER, ORC_FIGHTER, ctx.orc_fighter_stat().int_list().value));
        recommendedStats.add(statFrom(RACE_ORC, CLASS_ORC_SHAMAN, ORC_SHAMAN, ctx.orc_shaman_stat().int_list().value));

        // dwarf
        recommendedStats.add(statFrom(RACE_DWARF, CLASS_DWARF_APPRENTICE, DWARF_APPRENTICE, ctx.dwarf_apprentice_stat().int_list().value));

        // kamael
        recommendedStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_M_SOLDIER, KAMAEL_M_SOLDIER, ctx.kamael_m_soldier_stat().int_list().value));
        recommendedStats.add(statFrom(RACE_KAMAEL, CLASS_KAMAEL_F_SOLDIER, KAMAEL_F_SOLDIER, ctx.kamael_f_soldier_stat().int_list().value));
    }

    @Override
    public void exitInitial_start_point(SettingsParser.Initial_start_pointContext ctx) {
        for (SettingsParser.Start_pointContext sctx : ctx.start_point()) {
            List<Vector3D> points = sctx.point()
                    .stream()
                    .map(pctx -> pctx.vector3D_object().value)
                    .collect(Collectors.toList());

            List<String> classes = sctx.class_().identifier_list().value;
            classes.stream().forEach(c -> initialStartPoints.put(c, Collections.unmodifiableList(points)));
        }
    }

    private CharacterStat statFrom(int raceId, int classId, String name, List<Integer> stats) {
        return new CharacterStat(raceId, classId, name, stats);
    }

    @PostConstruct
    private void parse() throws IOException {
        log.info("Loading data file: setting.txt");
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
