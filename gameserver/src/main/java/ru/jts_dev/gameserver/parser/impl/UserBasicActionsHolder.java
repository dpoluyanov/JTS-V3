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
import ru.jts_dev.gameserver.constants.ActionHandlerType;
import ru.jts_dev.gameserver.parser.UserBasicActionsBaseListener;
import ru.jts_dev.gameserver.parser.UserBasicActionsLexer;
import ru.jts_dev.gameserver.parser.UserBasicActionsParser;
import ru.jts_dev.gameserver.parser.data.action.Action;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camelion
 * @since 20.01.16
 */
@Component
public class UserBasicActionsHolder extends UserBasicActionsBaseListener {
    private static final Logger log = LoggerFactory.getLogger(UserBasicActionsHolder.class);
    private final Map<Integer, Action> actionsData = new HashMap<>();
    @Autowired
    private ApplicationContext context;

    public Map<Integer, Action> getActionsData() {
        return Collections.unmodifiableMap(actionsData);
    }

    @Override
    public void exitAction(UserBasicActionsParser.ActionContext ctx) {
        int id = ctx.id().value;
        ActionHandlerType handler = ctx.handler() == null ? null : ctx.handler().value;
        Object option = ctx.option() == null ? null : ctx.option().value;

        Action action = new Action(id, handler, option);

        assert !actionsData.containsKey(action.getId()) : "Duplicate UserBasicAction Id";

        actionsData.put(action.getId(), action);
    }

    @PostConstruct
    private void parse() throws IOException {
        log.info("Loading data file: userbasicaction.txt");
        Resource file = context.getResource("scripts/userbasicaction.txt");
        try (InputStream is = file.getInputStream()) {
            ANTLRInputStream input = new ANTLRInputStream(is);
            UserBasicActionsLexer lexer = new UserBasicActionsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            UserBasicActionsParser parser = new UserBasicActionsParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
