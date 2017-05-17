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

package ru.jts_dev.gameserver.packets.in;

import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Opcode(0x92)
public class RequestAllyCrest extends IncomingMessageWrapper {
    private int crestId;

    @Override
    public void prepare() {
        crestId = readInt();
    }

    @Override
    public void run() {
        // TODO: 06.01.16 send crest id
        throw new UnsupportedOperationException("Not released yet");
    }
}
