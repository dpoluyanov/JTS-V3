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

package ru.jts_dev.gameserver.parser.data.action;

import ru.jts_dev.gameserver.constants.ActionHandlerType;

/**
 * @author Camelion
 * @since 20.01.16
 */
public final class Action {
    private final int id;
    private final ActionHandlerType handler;
    private final Object option;

    public Action(int id, ActionHandlerType handler, Object option) {
        this.id = id;
        this.handler = handler;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public ActionHandlerType getHandler() {
        return handler;
    }

    public Object getOption() {
        return option;
    }
}
