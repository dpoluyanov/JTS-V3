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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yorie, AN3O
 */
public class CommandHandler<TCommandType> implements ICommandHandler<TCommandType> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private ArrayList<TCommandType> commands;

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TCommandType> getCommandList() {
        if (commands == null) {
            commands = new ArrayList<>();
            for (Method method : getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(TextCommand.class)) {
                    String commandName = method.getAnnotation(TextCommand.class).value();

                    if (!commands.contains(commandName)) {
                        commands.add((TCommandType) commandName);
                    }
                } else if (method.isAnnotationPresent(NumericCommand.class)) {
                    Integer command = method.getAnnotation(NumericCommand.class).value();
                    if (!commands.contains(command)) {
                        commands.add((TCommandType) command);
                    }
                }
            }
            commands.trimToSize();
        }

        return commands;
    }
}
