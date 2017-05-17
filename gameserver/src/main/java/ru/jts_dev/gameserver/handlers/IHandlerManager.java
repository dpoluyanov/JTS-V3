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

/**
 * @author Yorie
 */
public interface IHandlerManager<TCommandType, THandlerType> {
    /**
     * @return Count of registered handlers in current handler manager.
     */
    int size();

    /**
     * Removes command for ever.
     *
     * @param command Command name.
     */
    void removeCommand(TCommandType command);

    /**
     * Disables command till it will be enabled.
     *
     * @param command Command name.
     */
    void disableCommand(TCommandType command);

    /**
     * Enable command if it was disabled.
     *
     * @param command Command name.
     */
    void enableCommand(TCommandType command);

    /**
     * @param command Command name.
     * @return True if given command is enabled.
     */
    boolean isCommandEnabled(TCommandType command);

    /**
     * Executes command with given params.
     *
     * @param params Params container.
     * @return True on successful command execution.
     */
    boolean execute(HandlerParams<TCommandType> params);
}
