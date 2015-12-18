package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 */
public class Repeat extends Task {
    private final Task task;
    private final int originalTimes;
    private int times;

    public Repeat(final Task task) {
        this.task = task;
        this.times = -1; // infinite
        this.originalTimes = times;
    }

    public Repeat(final Task task, final int times) {
        if (times < 1) {
            throw new IllegalArgumentException("Can't repeat negative times.");
        }
        this.task = task;
        this.times = times;
        this.originalTimes = times;
    }

    @Override
    public void start() {
        super.start();
        task.start();
    }

    @Override
    public void reset() {
        // reset counters
        this.times = originalTimes;
    }

    @Override
    public void act(final AiObject aiObject, GameCharacter gameCharacter) {
        if (task.isFailure()) {
            fail();
        } else if (task.isSuccess()) {
            if (times == 0) {
                succeed();
                return;
            }
            if (times > 0 || times <= -1) {
                times--;
                task.reset();
                task.start();
            }
        }
        if (task.isRunning()) {
            task.act(aiObject, gameCharacter);
        }
    }
}
