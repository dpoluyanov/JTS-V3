package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.handlers.voiced.Ping;

import javax.annotation.PostConstruct;

/**
 * Voiced command handlers manager.
 *
 * @author AN3O
 */
@HandlerList({
        Ping.class
})
@Service
public class VoicedHandlerManager extends TextHandlerManager {
    @PostConstruct
    private void load() {
        addHandlers(getClass().getAnnotation(HandlerList.class).value());
        log.info("Loaded {} voiced handlers.", size());
    }
}
