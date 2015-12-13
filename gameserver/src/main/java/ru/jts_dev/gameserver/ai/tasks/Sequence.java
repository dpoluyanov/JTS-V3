package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Java-man
 */
public class Sequence extends Task {
    private final Collection<Task> tasks = new LinkedList<>();
    private final Queue<Task> taskQueue = new LinkedList<>();
    private Task currentTask;

    public Sequence() {
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

        // check if there are more tasks in the queue
        // and if there are then step forward or set the sequence
        // state if finished
        if (taskQueue.peek() == null) {
            // we processed the last task in the sequence so set the state to that
            this.state = currentTask.getState();
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
