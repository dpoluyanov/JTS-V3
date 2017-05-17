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
