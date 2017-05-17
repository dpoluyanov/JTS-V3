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

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameSession;

import javax.annotation.Nullable;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.buffer.Unpooled.buffer;
import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Service
public class GameSessionService {
    private static final byte[] STATIC_KEY_PART = new byte[]{
            (byte) 0xc8, (byte) 0x27, (byte) 0x93, (byte) 0x01, (byte) 0xa1, (byte) 0x6c, (byte) 0x31, (byte) 0x97
    };

    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> accounts = new ConcurrentHashMap<>();

    private final Random random;

    @Autowired
    public GameSessionService(Random random) {
        this.random = random;
    }

    Map<String, GameSession> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

    /**
     * Note: this method can return null.
     * Always check returned value with null, and break, if check not passed.
     * <pre>
     *     GameSession gameSession = sessionService.getSessionById(getConnectionId());
     *     if(gameSession == null) return;
     * </pre>
     *
     * @param connectionId - connection identifier of session
     * @return stored GameSession or {@code null}
     */
    @Nullable
    public GameSession getSessionBy(String connectionId) {
        return sessions.getOrDefault(connectionId, null);
    }

    /**
     * Note: this method can return null.
     * Always check returned value with null, and break, if check not passed.
     * <pre>
     *     String account = sessionService.getAccountBy(getConnectionId());
     *     if(account == null) return;
     * </pre>
     *
     * @param connectionId - connection identifier of session
     * @return stored GameSession or {@code null}
     */
    @Nullable
    public String getAccountBy(String connectionId) {
        return accounts.getOrDefault(connectionId, null);
    }

    public void forcedClose(GameSession session) {
        session.getConnection().close();
    }

    private GameSession createSession(TcpConnection connection) {
        ByteBuf encryptKey = buffer(16, 16).order(ByteOrder.LITTLE_ENDIAN);
        // randomize first 8 bytes of key
        random.nextBytes(encryptKey.array());
        encryptKey.writerIndex(8);

        // and add 8 byte as static key part
        encryptKey.writeBytes(STATIC_KEY_PART);

        // then copy encrypt key to decrypt key
        ByteBuf decryptKey = copiedBuffer(encryptKey).order(ByteOrder.LITTLE_ENDIAN);

        // and pass keys to a game session object
        return new GameSession(connection, encryptKey, decryptKey);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    private void tcpConnectionEventListener(TcpConnectionEvent event) {
        sessions.put(event.getConnectionId(), createSession((TcpConnection) event.getSource()));
    }

    @EventListener
    private void tcpConnectionEventListener(TcpConnectionCloseEvent event) {
        sessions.remove(event.getConnectionId());
        accounts.remove(event.getConnectionId());
    }

    @EventListener
    public void accountLogged(AccountEvent event) {
        accounts.put(event.getConnectionId(), (String) event.getSource());
    }

    public static class AccountEvent extends ApplicationEvent {
        private final String connectionId;

        public AccountEvent(String connectionId, String login) {
            super(login);
            this.connectionId = connectionId;
        }

        public String getConnectionId() {
            return connectionId;
        }
    }
}
