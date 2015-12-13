package ru.jts_dev.gameserver.ai;

import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.ai.tasks.Task;
import ru.jts_dev.gameserver.ai.tasks.Tasks;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Component
public class AiService {
    public void runningAround(AiObject aiObject) {
        Task task = Tasks.repeatInfinite(Tasks.moveTo());
        aiObject.setTask(task);
    }
}
