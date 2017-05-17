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

import java.util.List;
import java.util.Map;

/**
 * @author AN3O
 */
public class CommandWrapper {
    private final List<String> args;
    private final Map<String, String> queryArgs;
    private String command;

    public CommandWrapper(String command, List<String> args, Map<String, String> queryArgs) {
        this.command = command;
        this.args = args;
        this.queryArgs = queryArgs;
    }

    /**
     * @return Command name.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Allows to replace command name and keep old arguments.
     *
     * @param command New command name.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getArgs() {
        return args;
    }

    public Map<String, String> getQueryArgs() {
        return queryArgs;
    }
}
