package ru.jts_dev.gameserver.ai;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.ai.tasks.Task;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AiObject {
    private final GameCharacter gameCharacter;

    private Task task;

    public AiObject(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
    }

    @Scheduled(fixedRate = 1000)
    private void aiTaskExecute() {
        if (task == null) {
            return;
        }

        if (task.getState() == null) {
            // hasn't started yet so we start it
            task.start();
        }

        task.act(this, gameCharacter);
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
