package ru.jts_dev.gameserver.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.ai.InternalMessage.SetTask;
import ru.jts_dev.gameserver.ai.tasks.Tasks;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Service
public class AiService {
    private final ApplicationEventPublisher publisher;

    @Autowired
    public AiService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void handleInternalMessage(InternalMessage internalMessage) {
        internalMessage.run();
    }

    public void runningAround(GameCharacter gameCharacter) {
        SetTask task = new SetTask(gameCharacter.getAiObject(), Tasks.moveTo());
        publisher.publishEvent(task);
    }
}
