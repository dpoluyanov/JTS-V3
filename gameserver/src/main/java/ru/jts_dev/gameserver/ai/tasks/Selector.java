package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Java-man
 */
public class Selector extends Task {
    private final Collection<Task> tasks = new LinkedList<>();
    private final Queue<Task> taskQueue = new LinkedList<>();
    private Task currentTask;

    public Selector() {
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
        taskQueue.addAll(tasks);
        currentTask = taskQueue.poll();
        currentTask.start();
    }

    @Override
    public void reset() {
        tasks.forEach(Task::reset);
    }

    @Override
    public void act(final AiObject aiObject, AiVariablesHolder aiVariablesHolder) {
        currentTask.act(aiObject, aiVariablesHolder);
        // if is still running, then carry on
        if (currentTask.isRunning()) {
            return;
        }

        // check if the task is successful and finish the sequence
        if (currentTask.isSuccess()) {
            succeed();
            return;
        }

        // We need to progress the sequence. If there are no more tasks
        // then the state is the last task's state. (Success for OR was already handled)
        if (taskQueue.peek() == null) {
            this.state = currentTask.getState();
        } else {
            currentTask = taskQueue.poll();
            currentTask.start();
        }
    }
}
