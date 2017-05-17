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
import ru.jts_dev.gameserver.ai.Weights;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 */
public abstract class Task {
    protected TaskState state;

    protected Task() {
    }

    public void start() {
        System.out.println(">>> Starting task: " + this.getClass().getSimpleName());
        this.state = TaskState.Running;
    }

    public abstract void reset();

    public abstract void act(AiObject aiObject, GameCharacter gameCharacter);

    protected void succeed() {
        System.out.println(">>> Task: " + this.getClass().getSimpleName() + " SUCCEEDED");
        this.state = TaskState.Success;
    }

    protected void fail() {
        System.out.println(">>> Task: " + this.getClass().getSimpleName() + " FAILED");
        this.state = TaskState.Failure;
    }

    public boolean isSuccess() {
        return state == TaskState.Success;
    }

    public boolean isFailure() {
        return state == TaskState.Failure;
    }

    public boolean isRunning() {
        return state == TaskState.Running;
    }

    public TaskState getState() {
        return state;
    }

    public boolean isMeetRequirements(final AiObject aiObject) {
        return true;
    }

    public int getWeight() {
        return Weights.NO_WEIGHT;
    }

    public enum TaskState {
        Success,
        Failure,
        Running
    }
}
