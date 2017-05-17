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

package ru.jts_dev.gameserver.ai;

import ru.jts_dev.gameserver.ai.tasks.Task;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.concurrent.Executors;
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
