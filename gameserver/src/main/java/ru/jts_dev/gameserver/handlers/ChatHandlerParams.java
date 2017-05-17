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

package ru.jts_dev.gameserver.handlers;

import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;

/**
 * Parameter container for chat command handlers only.
 *
 * @author Yorie
 */
public class ChatHandlerParams<Integer> extends HandlerParams<Integer> {
    private final String message;
    private final String target;

    public ChatHandlerParams(GameSession session, GameCharacter character, Integer command, String message, String target) {
        super(session, character, command);
        this.message = message;
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public String getTarget() {
        return target;
    }
}
