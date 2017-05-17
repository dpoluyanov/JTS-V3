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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.messaging.GameServerInfo;
import ru.jts_dev.gameserver.config.GameServerConfig;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Camelion
 * @since 09.12.15
 */
@Service
public class AuthServerService {
    private final JmsTemplate jmsTemplate;
    private final GameServerConfig gameServerConfig;

    @Autowired
    public AuthServerService(JmsTemplate jmsTemplate, GameServerConfig gameServerConfig) {
        this.jmsTemplate = jmsTemplate;
        this.gameServerConfig = gameServerConfig;
    }

    /**
     * send game server status to auth server
     */
    @PostConstruct
    public void register() throws UnknownHostException {
        byte serverId = gameServerConfig.getServerId();
        String host = gameServerConfig.getHost();
        int port = gameServerConfig.getPort();
        jmsTemplate.convertAndSend("gameServersQueue", new GameServerInfo(serverId, InetAddress.getByName(host), port));
    }
}
