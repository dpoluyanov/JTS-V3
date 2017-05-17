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

package ru.jts_dev.authserver.model;

import java.security.KeyPair;

/**
 * @author Camelion
 * @since 03.12.15
 */
public class AuthSession {
    private final String connectionId;
    private final int sessionId;
    private final KeyPair rsaKeyPair;
    private final byte[] blowfishKey;
    private final int loginKey1;
    private final int loginKey2;
    private final int gameKey1;
    private final int gameKey2;

    public AuthSession(String connectionId, int sessionId, KeyPair rsaKeyPair, byte[] blowfishKey,
                       int loginKey1, int loginKey2, int gameKey1, int gameKey2) {
        this.connectionId = connectionId;
        this.sessionId = sessionId;
        this.rsaKeyPair = rsaKeyPair;
        this.blowfishKey = blowfishKey;
        this.loginKey1 = loginKey1;
        this.loginKey2 = loginKey2;
        this.gameKey1 = gameKey1;
        this.gameKey2 = gameKey2;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public KeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

    public byte[] getBlowfishKey() {
        return blowfishKey;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getLoginKey2() {
        return loginKey2;
    }

    public int getLoginKey1() {
        return loginKey1;
    }

    public int getGameKey1() {
        return gameKey1;
    }

    public int getGameKey2() {
        return gameKey2;
    }
}
