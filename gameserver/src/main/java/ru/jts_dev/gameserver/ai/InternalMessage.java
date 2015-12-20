package ru.jts_dev.gameserver.ai;

import ru.jts_dev.gameserver.ai.tasks.Task;

/**
 * @author Java-man
 * @since 21.12.2015
 */
abstract class InternalMessage {
    abstract void run();

    static class SetTask extends InternalMessage {
        private final AiObject aiObject;
        private final Task task;

        public SetTask(AiObject aiObject, Task task) {
            this.aiObject = aiObject;
            this.task = task;
        }

        @Override
        public void run() {
            aiObject.setTask(task);
        }
    }
}
