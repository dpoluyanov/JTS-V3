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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.messaging.GameServerInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Camelion
 * @since 09.12.15
 */
@Service
public class GameServerService {
    private static final Logger log = LoggerFactory.getLogger(GameServerService.class);
    private Set<GameServerInfo> gameServers = new HashSet<>();

    public Set<GameServerInfo> getGameServers() {
        return Collections.unmodifiableSet(gameServers);
    }

    @JmsListener(destination = "gameServersQueue")
    public void processGameServerInfo(GameServerInfo gameServerInfo) {
        if (!gameServers.contains(gameServerInfo)) {
            log.info("Connected new GameServer with id: "
                    + gameServerInfo.getServerId() + " from: " + gameServerInfo.getAddress());
        }
        gameServers.add(gameServerInfo);
    }
}
