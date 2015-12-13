package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;

import java.util.*;

/**
 * @author Java-man
 */
public class ConditionalSequence extends Task {
    private final List<Task> tasks = new LinkedList<>();
    private final Collection<Task> taskQueue = new LinkedList<>();
    private Task currentTask;

    public ConditionalSequence() {
        this.currentTask = null;
    }

    public void addTask(final Task task) {
        tasks.add(task);
    }

    @Override
    public void start() {
        // start the current sequence
        super.start();
        // reset the current queue and copy the tasks from setup
        taskQueue.clear();
        tasks.sort(Comparator.comparingInt(Task::getWeight));
        taskQueue.addAll(tasks);
    }

    @Override
    public void reset() {
        tasks.forEach(Task::reset);
    }

    @Override
    public void act(final AiObject aiObject, AiVariablesHolder aiVariablesHolder) {
        final Optional<Task> taskOptional = taskQueue.stream().filter(task -> task.isMeetRequirements(aiObject)).findFirst();
        if (taskOptional.isPresent()) {
            currentTask = taskOptional.get();
            currentTask.start();
            currentTask.act(aiObject, aiVariablesHolder);

            succeed();
        } else
            fail();
    }
}
