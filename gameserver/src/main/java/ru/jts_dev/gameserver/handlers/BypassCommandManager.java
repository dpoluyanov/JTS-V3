package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.handlers.bypasses.VoiceCommand;

import javax.annotation.PostConstruct;

@HandlerList({
        VoiceCommand.class,
})
@Service
public class BypassCommandManager extends TextHandlerManager {
    @PostConstruct
    private void load() {
        addHandlers(getClass().getAnnotation(HandlerList.class).value());
        log.info("Loaded {} bypass handlers.", size());
    }
}