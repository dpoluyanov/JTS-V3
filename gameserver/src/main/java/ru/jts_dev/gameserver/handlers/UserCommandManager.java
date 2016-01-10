package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.handlers.usercommands.Loc;

import javax.annotation.PostConstruct;

@HandlerList({
        Loc.class,
})
@Service
public class UserCommandManager extends NumHandlerManager {
    @PostConstruct
    private void load() {
        addHandlers(getClass().getAnnotation(HandlerList.class).value());
        log.info("Loaded {} user command handlers", size());
    }
}
