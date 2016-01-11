package ru.jts_dev.gameserver.parser.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.parser.PCParametersBaseListener;
import ru.jts_dev.gameserver.parser.PCParametersLexer;
import ru.jts_dev.gameserver.parser.PCParametersParser;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Camelion
 * @since 05.01.16
 */
@Component
public class PCParametersHolder extends PCParametersBaseListener {
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
    @Autowired
    private ApplicationContext context;
    private Map<String, List<Double>> collisionBoxes = new HashMap<>();

    /**
     * @param sex      - male = 0, female = 1
     * @param statName - {@link CharacterStat#HUMAN_FIGHTER, or etc}
     * @return - String representation of sex and stat name
     */
    // TODO: 05.01.16 replace with enum
    @Cacheable(cacheNames = "pcParameterName", key = "#statName + \"_\" + #sex")
    public static String toPCParameterName(int sex, String statName) {
        String name = "";
        switch (statName) {
            case CharacterStat.HUMAN_FIGHTER:
                name = sex == 0 ? MFIGHTER : FFIGHTER;
                break;
            case CharacterStat.HUMAN_MAGICIAN:
                name = sex == 0 ? MMAGIC : FMAGIC;
                break;
            case CharacterStat.ELF_FIGHTER:
                name = sex == 0 ? MELF_FIGHTER : FELF_FIGHTER;
                break;
            case CharacterStat.ELF_MAGICIAN:
                name = sex == 0 ? MELF_MAGIC : FELF_MAGIC;
                break;
            case CharacterStat.DARKELF_FIGHTER:
                name = sex == 0 ? MDARKELF_FIGHTER : FDARKELF_FIGHTER;
                break;
            case CharacterStat.DARKELF_MAGICIAN:
                name = sex == 0 ? MDARKELF_MAGIC : FDARKELF_MAGIC;
                break;
            case CharacterStat.ORC_FIGHTER:
                name = sex == 0 ? MORC_FIGHTER : FORC_FIGHTER;
                break;
            case CharacterStat.ORC_SHAMAN:
                name = sex == 0 ? MSHAMAN : FSHAMAN;
                break;
            case CharacterStat.DWARF_APPRENTICE:
                name = sex == 0 ? MDWARF_FIGHTER : FDWARF_FIGHTER;
                break;
            case CharacterStat.KAMAEL_F_SOLDIER:
                name = FKAMAEL_SOLDIER;
                break;
            case CharacterStat.KAMAEL_M_SOLDIER:
                name = MKAMAEL_SOLDIER;
                break;
        }

        return name;
    }

    public Map<String, List<Double>> getCollisionBoxes() {
        return Collections.unmodifiableMap(collisionBoxes);
    }

    @Override
    public void exitPc_collision_box_table(PCParametersParser.Pc_collision_box_tableContext ctx) {
        for (PCParametersParser.Collision_statContext csctx : ctx.collision_stat()) {
            String pcClassName = csctx.pc_name().getText();

            List<Double> collisions = csctx.double_list().value;

            collisionBoxes.put(pcClassName, collisions);
        }
    }

    @PostConstruct
    private void parse() throws IOException {
        Resource file = context.getResource("scripts/pc_parameter.txt");
        try (InputStream is = file.getInputStream()) {
            ANTLRInputStream input = new ANTLRInputStream(is);
            PCParametersLexer lexer = new PCParametersLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PCParametersParser parser = new PCParametersParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
