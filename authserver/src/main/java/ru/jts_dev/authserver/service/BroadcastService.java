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

package ru.jts_dev.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Java-man
 * @since 19.01.2016
 */
@Service
public class BroadcastService {
    private final MessageChannel packetChannel;

    @Autowired
    public BroadcastService(MessageChannel packetChannel) {
        this.packetChannel = packetChannel;
    }

    private void send(String connectionId, OutgoingMessageWrapper message) {
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }

    public void send(AuthSession session, OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }
}
