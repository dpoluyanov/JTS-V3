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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.common.packets.StaticOutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;

import java.util.stream.Stream;

/**
 * @author Java-man
 * @since 19.01.2016
 */
@Service
public class BroadcastService {
    private static final int THRESHOLD = 25;
    private static final Logger logger = LoggerFactory.getLogger(BroadcastService.class);

    private final GameSessionService sessionService;
    private final MessageChannel packetChannel;

    @Autowired
    public BroadcastService(MessageChannel packetChannel, GameSessionService sessionService) {
        this.packetChannel = packetChannel;
        this.sessionService = sessionService;
    }

    public final void sendToAll(final OutgoingMessageWrapper message) {
        Stream<GameSession> stream = sessionService.getSessions().size() > THRESHOLD ?
                sessionService.getSessions().values().parallelStream() : sessionService.getSessions().values().stream();
        stream.forEach(gameSession -> send(gameSession.getConnectionId(), message));
    }

    public final void send(final GameSession session, final OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }

    public final void send(final String connectionId, OutgoingMessageWrapper message) {
        if (message.isStatic() && message instanceof StaticOutgoingMessageWrapper) {
            send(connectionId, message);
            logger.trace("Clone {} packet", message.getClass().getSimpleName());
            return;
        }
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }
}
