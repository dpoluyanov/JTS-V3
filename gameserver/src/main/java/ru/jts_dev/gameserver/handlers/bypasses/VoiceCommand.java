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

package ru.jts_dev.gameserver.handlers.bypasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.*;

import java.util.Collections;
import java.util.List;

/**
 * @author AN3O
 */
@Component
public class VoiceCommand extends CommandHandler<String> {
    @Autowired
    private VoicedHandlerManager voicedHandlerManager;

    @TextCommand
    public boolean voice(BypassHandlerParams params) {
        // only voice commands allowed
        if (!params.getArgs().isEmpty() && params.getArgs().get(0).startsWith(".")) {
            String command = params.getArgs().get(0);

            if (command.isEmpty()) {
                return false;
            }

            command = command.substring(1);

            List<String> args = params.getArgs().size() > 1 ? params.getArgs().subList(1, params.getArgs().size()) : Collections.<String>emptyList();

            if (!command.isEmpty()) {
                voicedHandlerManager.execute(new HandlerParams<>(params.getSession(), params.getCharacter(), command,
                        args, Collections.<String, String>emptyMap()));
            }
        }
        return false;
    }
}