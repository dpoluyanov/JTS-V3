package ru.jts_dev.gameserver.ai;

import ru.jts_dev.gameserver.ai.tasks.Task;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Java-man
 * @since 13.12.2015
 */
public class AiObject {
    // TODO scheduler
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    private final GameCharacter gameCharacter;

    private AtomicReference<Task> taskAtomicReference;

    public AiObject(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
        scheduler.scheduleAtFixedRate(this::aiTaskExecute, 500, 500, TimeUnit.MILLISECONDS);
    }

    private void aiTaskExecute() {
        if (taskAtomicReference == null) {
            return;
        }

        Task task = taskAtomicReference.get();

        if (task == null) {
            return;
        }

        if (task.getState() == null) {
            // hasn't started yet so we start it
            task.start();
        }

        task.act(this, gameCharacter);
        taskAtomicReference.set(null);
    }

    void setTask(Task task) {
        taskAtomicReference = new AtomicReference<>(task);
    }
}
