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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.util.Encoder;
import ru.jts_dev.common.id.IdPool;

import java.security.KeyPairGenerator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Camelion
 * @since 07.12.15
 */
@Service
public class AuthSessionService {
    private final Map<String, AuthSession> sessions = new ConcurrentHashMap<>();

    private final Random random;
    private final KeyPairGenerator keyPairGenerator;
    private final IdPool idPool;

    @Autowired
    public AuthSessionService(Random random, IdPool idPool, KeyPairGenerator keyPairGenerator) {
        this.random = random;
        this.idPool = idPool;
        this.keyPairGenerator = keyPairGenerator;
    }

    public AuthSession getSessionBy(String connectionId) {
        if (!sessions.containsKey(connectionId))
            throw new NullPointerException("authSession is null for " + connectionId);

        return sessions.get(connectionId);
    }

    private AuthSession createSession(String connectionId) {
        byte[] key = new byte[Encoder.BLOWFISH_KEY_SIZE];
        random.nextBytes(key);
        return new AuthSession(connectionId, idPool.borrow(), keyPairGenerator.generateKeyPair(), key,
                random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    private void tcpConnectionEventListener(TcpConnectionEvent event) {
        sessions.put(event.getConnectionId(), createSession(event.getConnectionId()));
    }

    @EventListener
    private void tcpConnectionEventListener(TcpConnectionCloseEvent event) {
        AuthSession session = sessions.remove(event.getConnectionId());
        idPool.release(session.getSessionId());
    }
}
