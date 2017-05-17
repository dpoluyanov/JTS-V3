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

import ru.jts_dev.gameserver.ai.tasks.impl.MoveTo;

/**
 * Static convenience methods to create tasks
 *
 * @author Java-man
 */
public final class Tasks {
    private Tasks() {
    }

    public static Task sequence(final Task... tasks) {
        final Sequence sequence = new Sequence();
        for (final Task task : tasks) {
            sequence.addTask(task);
        }
        return sequence;
    }

    public static Task selector(final Task... tasks) {
        final Selector selector = new Selector();
        for (final Task task : tasks) {
            selector.addTask(task);
        }
        return selector;
    }

    public static Task conditionalSequence(final Task... tasks) {
        final ConditionalSequence sequence = new ConditionalSequence();
        for (final Task task : tasks) {
            sequence.addTask(task);
        }
        return sequence;
    }

    public static MoveTo moveTo() {
        return new MoveTo();
    }

    public static Task repeatInfinite(final Task task) {
        return new Repeat(task);
    }

    public static Task repeat(final Task task, final int times) {
        return new Repeat(task, times);
    }
}
