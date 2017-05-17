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

package ru.jts_dev.gameserver.model;

import io.netty.buffer.ByteBuf;
import org.springframework.integration.ip.tcp.connection.TcpConnection;

/**
 * @author Camelion
 * @since 13.12.15
 */
public final class GameSession {
    private static final int KEY_SIZE = 16;
    private final TcpConnection connection;
    private final ByteBuf encryptKey;
    private final ByteBuf decryptKey;

    private int playKey;

    public GameSession(final TcpConnection connection, final ByteBuf encryptKey, final ByteBuf decryptKey) {
        if (encryptKey.readableBytes() != KEY_SIZE)
            throw new RuntimeException("encryptKey must be 16 bytes");
        if (decryptKey.readableBytes() != KEY_SIZE)
            throw new RuntimeException("decryptKey must be 16 bytes");

        this.connection = connection;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    public TcpConnection getConnection() {
        return connection;
    }

    public String getConnectionId() {
        return connection.getConnectionId();
    }

    public ByteBuf getEncryptKey() {
        return encryptKey;
    }

    public ByteBuf getDecryptKey() {
        return decryptKey;
    }

    public int getPlayKey() {
        return playKey;
    }

    public void setPlayKey(final int playKey) {
        this.playKey = playKey;
    }
}
