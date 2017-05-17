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

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterSelectionInfo;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

import java.util.List;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Opcode(second = 0x36)
public class GotoLobby extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private GameServerConfig gameServerConfig;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final String accountName = sessionService.getAccountBy(getConnectionId());
        final List<GameCharacter> characters = repository.findAllByAccountName(accountName);

        broadcastService.send(session,
                new CharacterSelectionInfo(characters, session.getPlayKey(), gameServerConfig.isCharCreationDisabled()));
    }
}
