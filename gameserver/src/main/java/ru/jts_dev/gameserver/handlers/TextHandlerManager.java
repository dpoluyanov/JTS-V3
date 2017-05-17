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

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Handler manager for text commands.
 *
 * @author Yorie
 */
@Component
public class TextHandlerManager extends HandlerManager<String, ICommandHandler<String>> {
    /**
     * Adds all handlers for enumerated handler classes of manager.
     * Handler commands detected when {link @TextCommand} annotation present on method.
     *
     * @param classes List of handler classes.
     */
    @SuppressWarnings("unchecked")
    protected void addHandlers(Class<? extends ICommandHandler>[] classes) {
        for (Class<? extends ICommandHandler> cls : classes) {
            try {
                Class<? extends ICommandHandler<String>> castedClass = (Class<? extends ICommandHandler<String>>) cls;
                ICommandHandler<String> handler = getHandlerInstance(castedClass);

                if (handler != null) {
                    for (Method method : getAnnotatedMethods(castedClass, TextCommand.class)) {
                        String command = method.getAnnotation(TextCommand.class).value();

                        if (command.isEmpty()) {
                            command = method.getName();
                        }

                        command = command.toLowerCase();

                        addHandler(command, handler, method);
                    }
                }
            } catch (Exception e) {
                log.error("Cannot register handler for class [" + cls.getName() + "].", e);
            }
        }
    }

    @Override
    public boolean execute(HandlerParams<String> params) {
        params.setCommand(params.getCommand().toLowerCase());
        return super.execute(params);
    }
}
