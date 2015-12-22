package ru.jts_dev.gameserver.ai;

import org.springframework.stereotype.Service;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;
import ru.jts_dev.gameserver.ai.tasks.Tasks;
import ru.jts_dev.gameserver.model.GameCharacter;

import javax.annotation.PostConstruct;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Service
public class AiService {
    private final Broadcaster<InternalMessage> broadcaster = Broadcaster.create();

    @PostConstruct
    private void startBroadCaster() {
        broadcaster
                // dispatch onto a Thread other than 'main'
                .dispatchOn(Environment.cachedDispatcher())
                .consume(InternalMessage::run);
    }

    public void runningAround(GameCharacter gameCharacter) {
        broadcaster.onNext(new InternalMessage.SetTask(gameCharacter.getAiObject(), Tasks.moveTo()));
    }
}
