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

import org.springframework.context.ApplicationEvent;
import ru.jts_dev.gameserver.ai.tasks.Task;

/**
 * @author Java-man
 * @since 21.12.2015
 */
abstract class InternalMessage extends ApplicationEvent {
    public InternalMessage(AiObject aiObject) {
        super(aiObject);
    }

    abstract void run();

    static class SetTask extends InternalMessage {
        private final AiObject aiObject;
        private final Task task;

        public SetTask(AiObject aiObject, Task task) {
            super(aiObject);
            this.aiObject = aiObject;
            this.task = task;
        }

        @Override
        public void run() {
            aiObject.setTask(task);
        }
    }
}
