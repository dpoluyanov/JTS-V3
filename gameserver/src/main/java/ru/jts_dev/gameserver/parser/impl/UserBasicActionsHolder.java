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
