package ru.jts_dev.gameserver.parser.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.parser.PCParametersBaseListener;
import ru.jts_dev.gameserver.parser.PCParametersLexer;
import ru.jts_dev.gameserver.parser.PCParametersParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camelion
 * @since 05.01.16
 */
@Component
public class PCParametersData extends PCParametersBaseListener {
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
    private Map<String, double[]> collisionBoxes = new HashMap<>();

    public Map<String, double[]> getCollisionBoxes() {
        return Collections.unmodifiableMap(collisionBoxes);
    }

    @Override
    public void exitPc_collision_box_table(PCParametersParser.Pc_collision_box_tableContext ctx) {
        for (PCParametersParser.Collision_statContext csctx : ctx.collision_stat()) {
            String pcClassName = csctx.pc_name().getText();

            double[] collisions = convertFromDoubleArray(csctx.double_array());

            collisionBoxes.put(pcClassName, collisions);
        }
    }

    /**
     * Converts ANTLR Generated {@link ru.jts_dev.gameserver.parser.PCParametersParser.Double_arrayContext}
     * to Java {@code double[]} array
     *
     * @param ctx - parser {@link ru.jts_dev.gameserver.parser.PCParametersParser.Double_arrayContext}
     * @return - filled double[] array
     */
    private double[] convertFromDoubleArray(PCParametersParser.Double_arrayContext ctx) {
        double[] array = new double[ctx.double_object().size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = Double.valueOf(ctx.double_object(i).getText());
        }

        return array;
    }

    @PostConstruct
    public void parse() throws IOException {
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
