/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import ru.jts_dev.gameserver.parser.SettingsParser.*;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Camelion
 * @since 15.12.15
 */
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

    public final Map<CharacterClass, List<Vector3D>> getInitialStartPoints() {
        return Collections.unmodifiableMap(initialStartPoints);
    }

    public final Map<CharacterClass, Map<String, Integer>> getInitialEquipments() {
        return Collections.unmodifiableMap(initialEquipments);
    }

    public final List<CharacterStat> getMinimumStats() {
        return Collections.unmodifiableList(minimumStats);
    }

    public final List<CharacterStat> getRecommendedStats() {
        return Collections.unmodifiableList(recommendedStats);
    }

    public final List<CharacterStat> getMaximumStats() {
        return Collections.unmodifiableList(maximumStats);
    }

    @Override
    public final void exitMinimum_stat(final Minimum_statContext ctx) {
        minimumStats.addAll(ctx.value);
    }

    @Override
    public final void exitMaximum_stat(final Maximum_statContext ctx) {
        maximumStats.addAll(ctx.value);
    }

    @Override
    public final void exitRecommended_stat(final Recommended_statContext ctx) {
        recommendedStats.addAll(ctx.value);
    }

    @Override
    public final void exitInitial_start_point(final Initial_start_pointContext ctx) {
        for (final Start_pointContext sctx : ctx.start_point()) {
            final List<Vector3D> points = sctx.points;
            final List<CharacterClass> classes = sctx.klasses;

            classes.stream().forEach(cls -> initialStartPoints.put(cls, Collections.unmodifiableList(points)));
        }
    }

    /**
     * fills map with initial character equipment
     *
     * @param ctx - parsed context
     */
    @Override
    public final void exitInitial_equipment(final Initial_equipmentContext ctx) {
        for (final Character_equipmentContext cectx : ctx.character_equipment()) {
            final CharacterClass klass = cectx.klass;
            final Map<String, Integer> equipment = cectx.equipmentMap;

            initialEquipments.put(klass, Collections.unmodifiableMap(equipment));
        }
        super.exitInitial_equipment(ctx);
    }

    @PostConstruct
    private void parse() throws IOException {
        log.info("Loading data file: setting.txt");
        final Resource file = context.getResource("scripts/setting.txt");
        try (InputStream is = file.getInputStream()) {
            final ANTLRInputStream input = new ANTLRInputStream(is);
            final SettingsLexer lexer = new SettingsLexer(input);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final SettingsParser parser = new SettingsParser(tokens);

            final ParseTree tree = parser.file();
            final ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
