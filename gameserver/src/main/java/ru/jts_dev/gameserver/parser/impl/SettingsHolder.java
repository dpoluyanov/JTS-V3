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
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.parser.SettingsBaseListener;
import ru.jts_dev.gameserver.parser.SettingsLexer;
import ru.jts_dev.gameserver.parser.SettingsParser;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Camelion
 * @since 15.12.15
 */
// TODO: 05.01.16 handle initial equipment
@Component
public class SettingsHolder extends SettingsBaseListener {
    private static final Logger log = LoggerFactory.getLogger(SettingsHolder.class);
    private final Map<CharacterClass, Map<String, Integer>> initialEquipments = new HashMap<>();
    private final Map<CharacterClass, List<Vector3D>> initialStartPoints = new EnumMap<>(CharacterClass.class);
    private final List<CharacterStat> minimumStats = new ArrayList<>();
    private final List<CharacterStat> recommendedStats = new ArrayList<>();
    private final List<CharacterStat> maximumStats = new ArrayList<>();
    @Autowired
    private ApplicationContext context;

    public Map<CharacterClass, List<Vector3D>> getInitialStartPoints() {
        return Collections.unmodifiableMap(initialStartPoints);
    }

    public Map<CharacterClass, Map<String, Integer>> getInitialEquipments() {
        return Collections.unmodifiableMap(initialEquipments);
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
        minimumStats.addAll(ctx.value);
    }

    @Override
    public void exitMaximum_stat(SettingsParser.Maximum_statContext ctx) {
        maximumStats.addAll(ctx.value);
    }

    @Override
    public void exitRecommended_stat(SettingsParser.Recommended_statContext ctx) {
        recommendedStats.addAll(ctx.value);
    }

    @Override
    public void exitInitial_start_point(SettingsParser.Initial_start_pointContext ctx) {
        for (SettingsParser.Start_pointContext sctx : ctx.start_point()) {
            List<Vector3D> points = sctx.points;
            List<CharacterClass> classes = sctx.klasses;

            classes.stream().forEach(cls -> initialStartPoints.put(cls, Collections.unmodifiableList(points)));
        }
    }

    /**
     * fills map with initial character equipment
     *
     * @param ctx - parsed context
     */
    @Override
    public void exitInitial_equipment(SettingsParser.Initial_equipmentContext ctx) {
        for (SettingsParser.Character_equipmentContext cectx : ctx.character_equipment()) {
            CharacterClass klass = cectx.klass;
            Map<String, Integer> equipment = cectx.equipmentMap;

            initialEquipments.put(klass, Collections.unmodifiableMap(equipment));
        }
        super.exitInitial_equipment(ctx);
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
