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
 * @since 08.12.15
 */
public final class LoginOk extends OutgoingMessageWrapper {
    public static final int MAGIC_CONSTANT = 0x000003ea;
    public static final int ZEROS_LENGTH = 28;
    private final int key1;
    private final int key2;

    public LoginOk(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public void write() {
        writeByte(0x03);
        writeInt(key1);
        writeInt(key2);
        writeInt(0x00);
        writeInt(0x00);
        writeInt(MAGIC_CONSTANT);
        writeZero(ZEROS_LENGTH);
    }
}
