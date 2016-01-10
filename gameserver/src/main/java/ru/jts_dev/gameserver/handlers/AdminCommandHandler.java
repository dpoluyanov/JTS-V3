package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@HandlerList({
})
@Service
public class AdminCommandHandler extends TextHandlerManager {
    @PostConstruct
    private void load() {
        addHandlers(getClass().getAnnotation(HandlerList.class).value());
        log.info("Loaded {} admin handlers.", size());
    }
}
