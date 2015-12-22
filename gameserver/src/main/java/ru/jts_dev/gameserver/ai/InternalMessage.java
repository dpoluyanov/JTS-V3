package ru.jts_dev.gameserver.ai;

import org.springframework.context.ApplicationEvent;
import ru.jts_dev.gameserver.ai.tasks.Task;

/**
 * @author Java-man
 * @since 21.12.2015
 */
abstract class InternalMessage extends ApplicationEvent {
    public InternalMessage(AiObject aiObject) {
        super(aiObject);
    }

    abstract void run();

    static class SetTask extends InternalMessage {
        private final AiObject aiObject;
        private final Task task;

        public SetTask(AiObject aiObject, Task task) {
            super(aiObject);
            this.aiObject = aiObject;
            this.task = task;
        }

        @Override
        public void run() {
            aiObject.setTask(task);
        }
    }
}
