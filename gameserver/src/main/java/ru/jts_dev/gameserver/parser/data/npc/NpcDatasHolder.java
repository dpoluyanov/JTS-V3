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

package ru.jts_dev.gameserver.parser.data.npc;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.constants.NpcType;
import ru.jts_dev.gameserver.parser.NpcDatasBaseListener;
import ru.jts_dev.gameserver.parser.NpcDatasLexer;
import ru.jts_dev.gameserver.parser.NpcDatasParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camelion
 * @since 10.02.16
 */
@Component
public class NpcDatasHolder extends NpcDatasBaseListener {
    private static final Logger log = LoggerFactory.getLogger(NpcDatasHolder.class);
    private final Map<Integer, NpcData> npcData = new HashMap<>(10500);
    @Autowired
    private ApplicationContext context;

    @Override
    public void exitNpc(NpcDatasParser.NpcContext ctx) {
        final NpcType npcType = ctx.npc_type().value;
        final int npcId = ctx.npc_id().value;
        final String name = ctx.npc_name().value;
        NpcData data = new NpcData(npcType, npcId, name);

        assert !npcData.containsKey(npcId) : "Duplicate NpcId " + npcId;

        npcData.put(npcId, data);
    }

    @PostConstruct
    private void parse() throws IOException {
        log.info("Loading data file: npcdata.txt");
        final Resource file = context.getResource("scripts/npcdata.txt");
        try (InputStream is = file.getInputStream()) {
            final ANTLRInputStream input = new ANTLRInputStream(is);
            final NpcDatasLexer lexer = new NpcDatasLexer(input);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final NpcDatasParser parser = new NpcDatasParser(tokens);

            //parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
            //parser.setErrorHandler(new BailErrorStrategy());
            //parser.setProfile(false);

            long start = System.nanoTime();
            final ParseTree tree = parser.file();
            log.info("ParseTime: " + (System.nanoTime() - start) / 1_000_000);
            final ParseTreeWalker walker = new ParseTreeWalker();
            start = System.nanoTime();
            walker.walk(this, tree);
            log.info("WalkTime: " + (System.nanoTime() - start) / 1_000_000);
        }
    }

    public final Map<Integer, NpcData> getNpcData() {
        return Collections.unmodifiableMap(npcData);
    }
}
