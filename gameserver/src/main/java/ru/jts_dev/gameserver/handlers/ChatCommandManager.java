package ru.jts_dev.gameserver.handlers;

import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.handlers.chat.*;

import javax.annotation.PostConstruct;

@HandlerList({
        ChatAll.class,
        ChatAlliance.class,
        ChatClan.class,
        ChatHeroVoice.class,
        ChatPetition.class,
        ChatShout.class,
        ChatTell.class,
        ChatTrade.class,
        ChatParty.class,
        ChatWorld.class
})
@Service
public class ChatCommandManager extends NumHandlerManager {
    @PostConstruct
    private void load() {
        addHandlers(getClass().getAnnotation(HandlerList.class).value());
        log.info("Loaded {} chat handlers.", size());
    }
}