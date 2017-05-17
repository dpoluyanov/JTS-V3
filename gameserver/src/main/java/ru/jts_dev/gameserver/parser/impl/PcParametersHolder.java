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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.parser.PcParametersBaseListener;
import ru.jts_dev.gameserver.parser.PcParametersLexer;
import ru.jts_dev.gameserver.parser.PcParametersParser;
import ru.jts_dev.gameserver.parser.PcParametersParser.Collision_statContext;
import ru.jts_dev.gameserver.parser.PcParametersParser.Pc_collision_box_tableContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Camelion
 * @since 16.01.16
 */
@Component
public class PcParametersHolder extends PcParametersBaseListener {
    public static final int RADIUS = 0; // collision radius index
    public static final int HEIGHT = 1; // collision height index
    public static final String FFIGHTER = "FFighter";
    public static final String MFIGHTER = "MFighter";
    public static final String FMAGIC = "FMagic";
    public static final String MMAGIC = "MMagic";
    public static final String FELF_FIGHTER = "FElfFighter";
    public static final String MELF_FIGHTER = "MElfFighter";
    public static final String FELF_MAGIC = "FElfMagic";
    public static final String MELF_MAGIC = "MElfMagic";
    public static final String FDARKELF_FIGHTER = "FDarkelfFighter";
    public static final String MDARKELF_FIGHTER = "MDarkelfFighter";
    public static final String FDARKELF_MAGIC = "FDarkelfMagic";
    public static final String MDARKELF_MAGIC = "MDarkelfMagic";
    public static final String FORC_FIGHTER = "FOrcFighter";
    public static final String MORC_FIGHTER = "MOrcFighter";
    public static final String FSHAMAN = "FShaman";
    public static final String MSHAMAN = "MShaman";
    public static final String FDWARF_FIGHTER = "FDwarfFighter";
    public static final String MDWARF_FIGHTER = "MDwarfFighter";
    public static final String FKAMAEL_SOLDIER = "FKamaelSoldier";
    public static final String MKAMAEL_SOLDIER = "MKamaelSoldier";
    private static final Logger log = LoggerFactory.getLogger(PcParametersHolder.class);
    private final ApplicationContext context;
    private Map<String, List<Double>> collisionBoxes = new HashMap<>();

    @Autowired
    public PcParametersHolder(ApplicationContext context) {
        this.context = context;
    }

    /**
     * @param sex      - male = 0, female = 1
     * @param statName - {@link CharacterClass#HUMAN_FIGHTER, or etc}
     * @return - String representation of sex and stat name
     */
    // TODO: 05.01.16 replace with enum
    public static String toPCParameterName(int sex, CharacterClass statName) {
        String name = "";
        switch (statName) {
            case HUMAN_FIGHTER:
                name = sex == 0 ? MFIGHTER : FFIGHTER;
                break;
            case HUMAN_MAGICIAN:
                name = sex == 0 ? MMAGIC : FMAGIC;
                break;
            case ELF_FIGHTER:
                name = sex == 0 ? MELF_FIGHTER : FELF_FIGHTER;
                break;
            case ELF_MAGICIAN:
                name = sex == 0 ? MELF_MAGIC : FELF_MAGIC;
                break;
            case DARKELF_FIGHTER:
                name = sex == 0 ? MDARKELF_FIGHTER : FDARKELF_FIGHTER;
                break;
            case DARKELF_MAGICIAN:
                name = sex == 0 ? MDARKELF_MAGIC : FDARKELF_MAGIC;
                break;
            case ORC_FIGHTER:
                name = sex == 0 ? MORC_FIGHTER : FORC_FIGHTER;
                break;
            case ORC_SHAMAN:
                name = sex == 0 ? MSHAMAN : FSHAMAN;
                break;
            case DWARF_APPRENTICE:
                name = sex == 0 ? MDWARF_FIGHTER : FDWARF_FIGHTER;
                break;
            case KAMAEL_F_SOLDIER:
                name = FKAMAEL_SOLDIER;
                break;
            case KAMAEL_M_SOLDIER:
                name = MKAMAEL_SOLDIER;
                break;
        }

        return name;
    }

    public Map<String, List<Double>> getCollisionBoxes() {
        return Collections.unmodifiableMap(collisionBoxes);
    }

    @Override
    public void exitPc_collision_box_table(Pc_collision_box_tableContext ctx) {
        for (Collision_statContext csctx : ctx.collision_stat()) {
            String pcClassName = csctx.pc_name().getText();

            List<Double> collisions = csctx.double_list().value;

            collisionBoxes.put(pcClassName, collisions);
        }
    }

    @PostConstruct
    private void parse() throws IOException {
        log.info("Loading data file: pc_parameter.txt");
        Resource file = context.getResource("scripts/pc_parameter.txt");
        try (InputStream is = file.getInputStream()) {
            ANTLRInputStream input = new ANTLRInputStream(is);
            PcParametersLexer lexer = new PcParametersLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PcParametersParser parser = new PcParametersParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
