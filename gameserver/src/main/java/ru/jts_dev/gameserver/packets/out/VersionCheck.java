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

package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 13.12.15
 */
public class VersionCheck extends OutgoingMessageWrapper {
    private final byte[] key;
    private final byte serverId;

    public VersionCheck(byte[] key, byte serverId) {
        this.key = key;
        this.serverId = serverId;
    }

    @Override
    public void write() {
        writeByte(0x2E);
        writeByte(0x01); // 0 - wrong protocol, 1 protocol ok
        writeBytes(key);
        writeInt(0x01);
        writeInt(serverId);
        writeByte(0x00);
        // TODO: 13.12.15 obfuscation: https://github.com/l2jfree/svn/blob/15e8c48533246d30712806a099f09d17b522f86b/branches/genesis/l2jfree-core/src/main/java/com/l2jfree/gameserver/network/client/packets/sendable/characterless/VersionCheck.java
        writeInt(0x00); // Seed (obfuscation key)
    }
}
