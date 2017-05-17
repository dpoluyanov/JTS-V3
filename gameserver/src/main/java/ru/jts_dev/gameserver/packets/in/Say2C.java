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

package ru.jts_dev.gameserver.packets.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.constants.ChatType;
import ru.jts_dev.gameserver.handlers.ChatCommandManager;
import ru.jts_dev.gameserver.handlers.ChatHandlerParams;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ActionFailed;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;

/**
 * @author Java-man
 * @since 10.01.2016
 */
@Opcode(0x49)
public class Say2C extends IncomingMessageWrapper {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChatCommandManager chatCommandManager;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;

    private String text;
    private ChatType type;
    private String target;

    @Override
    public void prepare() {
        text = readString();
        type = readIntAs(ChatType.class);
        target = type == ChatType.TELL ? readString() : null;
    }

    @Override
    public void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final GameCharacter character = playerService.getCharacterBy(getConnectionId());

        if (type == null) {
            log.warn("Say2: Invalid type: {} Player : {} text: {}", type, character.getName(), text);
            broadcastService.send(session, ActionFailed.PACKET);
            // TODO character.logout();
            return;
        }

        if (text.isEmpty()) {
            log.warn(character.getName() + ": sending empty text. Possible packet hack!");
            broadcastService.send(session, ActionFailed.PACKET);
            // TODO character.logout();
            return;
        }

        chatCommandManager.execute(new ChatHandlerParams<>(session, character, type.ordinal(), text, target));
    }
}
