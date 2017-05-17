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

package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.packets.out.LoginFail;
import ru.jts_dev.authserver.packets.out.ServerList;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.service.BroadcastService;
import ru.jts_dev.authserver.service.GameServerService;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.authserver.packets.out.LoginFail.REASON_ACCESS_FAILED;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestServerList extends IncomingMessageWrapper {
    private int loginKey1, loginKey2;

    private final AuthSessionService authSessionService;
    private final BroadcastService broadcastService;
    private final GameServerService gameServerService;

    private final AbstractConnectionFactory connectionFactory;

    @Autowired
    public RequestServerList(BroadcastService broadcastService, AuthSessionService authSessionService, GameServerService gameServerService, AbstractConnectionFactory connectionFactory) {
        this.broadcastService = broadcastService;
        this.authSessionService = authSessionService;
        this.gameServerService = gameServerService;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void prepare() {
        loginKey1 = readInt();
        loginKey2 = readInt();
    }

    @Override
    public void run() {
        AuthSession session = authSessionService.getSessionBy(getConnectionId());

        if (session.getLoginKey1() == loginKey1 && session.getLoginKey2() == loginKey2) {
            broadcastService.send(session, new ServerList(gameServerService.getGameServers()));
        } else {
            broadcastService.send(session, new LoginFail(REASON_ACCESS_FAILED));
            connectionFactory.closeConnection(getConnectionId());
        }
    }
}
