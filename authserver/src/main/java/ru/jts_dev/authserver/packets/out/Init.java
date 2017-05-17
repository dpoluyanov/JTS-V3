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

package ru.jts_dev.authserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 30.11.15
 */
public final class Init extends OutgoingMessageWrapper {
    private final int sessionId;
    private final byte[] scrambledRSAKey;
    private final byte[] blowfishKey;

    public Init(int sessionId, byte[] scrambledRSAKey, byte[] blowfishKey) {
        this.sessionId = sessionId;
        this.scrambledRSAKey = scrambledRSAKey;
        this.blowfishKey = blowfishKey;
    }

    @Override
    public void write() {
        writeByte(0x00);
        writeInt(sessionId);
        writeInt(0x0000c621);
        writeBytes(scrambledRSAKey);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(0x00);
        writeBytes(blowfishKey);
        writeInt(0x04);
    }
}
