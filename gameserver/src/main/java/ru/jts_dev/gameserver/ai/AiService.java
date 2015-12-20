package ru.jts_dev.gameserver.ai;

import org.springframework.stereotype.Component;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;
import ru.jts_dev.gameserver.ai.tasks.Tasks;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Component
public class AiService {
    private final Broadcaster<InternalMessage> broadcaster = Broadcaster.create();

    public AiService() {
        broadcaster
                // dispatch onto a Thread other than 'main'
                .dispatchOn(Environment.cachedDispatcher())
                .consume(InternalMessage::run);
    }

    public void runningAround(AiObject aiObject) {
        broadcaster.onNext(new InternalMessage.SetTask(aiObject, Tasks.moveTo()));
    }
}
