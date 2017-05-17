/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.model.GameCharacter;

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
    public void act(final AiObject aiObject, GameCharacter gameCharacter) {
        currentTask.act(aiObject, gameCharacter);
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
