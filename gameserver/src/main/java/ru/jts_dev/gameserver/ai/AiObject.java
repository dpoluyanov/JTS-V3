package ru.jts_dev.gameserver.ai;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.ai.tasks.Task;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AiObject {
    private final AiVariablesHolder aiVariablesHolder;

    private Task task;

    public AiObject(AiVariablesHolder aiVariablesHolder) {
        this.aiVariablesHolder = aiVariablesHolder;
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

        task.act(this, aiVariablesHolder);
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
