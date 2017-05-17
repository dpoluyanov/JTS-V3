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

import java.util.List;
import java.util.Map;

/**
 * Parameter container for bypass command handlers only.
 *
 * @author Yorie
 */
public class BypassHandlerParams extends HandlerParams<String> {
    private final GameCharacter target;
    // TODO: Понапридумывали хуеты. Выпилить и сделать по-нормальному вместе с bypass-валидатором в L2PcInstance.
    private final String bypassSource;

    public BypassHandlerParams(GameSession session, GameCharacter character, String bypassSource, String command,
                               GameCharacter target) {
        super(session, character, command);
        this.target = target;
        this.bypassSource = bypassSource;
    }

    public BypassHandlerParams(GameSession session, GameCharacter character, String bypassSource, String command,
                               GameCharacter target, List<String> args, Map<String, String> queryArgs) {
        super(session, character, command, args, queryArgs);
        this.target = target;
        this.bypassSource = bypassSource;
    }

    /**
     * @return Returns bypass source command (source string with non-chunked parameters).
     */
    public String getSource() {
        return bypassSource;
    }

    public GameCharacter getTarget() {
        return target;
    }
}
