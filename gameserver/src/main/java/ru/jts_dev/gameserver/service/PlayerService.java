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

package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Service
public class PlayerService {
    private final Map<String, GameCharacter> characters = new ConcurrentHashMap<>();
    @Autowired
    private GameCharacterRepository gameCharacterRepository;

    public final GameCharacter getCharacterBy(final String connectionId) {
        return characters.get(connectionId);
    }

    @EventListener
    public final void characterSelected(final CharacterSelectedEvent event) {
        characters.put(event.getConnectionId(), (GameCharacter) event.getSource());
    }

    // TODO: 03.01.16 move to character logout event
    @EventListener
    private void tcpConnectionEventListener(final TcpConnectionCloseEvent event) {
        final GameCharacter character = characters.remove(event.getConnectionId());
        if (character != null) {
            gameCharacterRepository.save(character);
        }
    }

    @PreDestroy
    private void destroy() {
        gameCharacterRepository.save(characters.values());
    }

    public static class CharacterSelectedEvent extends ApplicationEvent {
        private static final long serialVersionUID = 2145294139798098206L;
        private final String connectionId;

        public CharacterSelectedEvent(final String connectionId, final GameCharacter character) {
            super(character);
            this.connectionId = connectionId;
        }

        public final String getConnectionId() {
            return connectionId;
        }
    }
}
