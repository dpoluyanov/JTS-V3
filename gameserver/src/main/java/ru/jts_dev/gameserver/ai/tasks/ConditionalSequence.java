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
    public void act(final AiObject aiObject, GameCharacter gameCharacter) {
        final Optional<Task> taskOptional = taskQueue.stream().filter(task -> task.isMeetRequirements(aiObject)).findFirst();
        if (taskOptional.isPresent()) {
            currentTask = taskOptional.get();
            currentTask.start();
            currentTask.act(aiObject, gameCharacter);

            succeed();
        } else
            fail();
    }
}
