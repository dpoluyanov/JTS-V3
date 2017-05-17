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

import ru.jts_dev.common.packets.StaticOutgoingMessageWrapper;

/**
 * Seven Signs Info
 * <p>
 * packet id 0x73
 * format: cc
 * <p>
 * Пример пакета с оффа (828 протокол):
 * 73 01 01
 * <p>
 * Возможные варианты использования данного пакета:
 * 0 0 - Обычное небо???
 * 1 1 - Dusk Sky
 * 2 2 - Dawn Sky???
 * 3 3 - Небо постепенно краснеет (за 10 секунд)
 * <p>
 * Возможно и другие вариации, эффект не совсем понятен.
 * 1 0
 * 0 1
 *
 * @author Java-man
 * @since 26.01.2016
 */
public class SSQInfo extends StaticOutgoingMessageWrapper {
    public static final SSQInfo NOTHING = new SSQInfo(0);
    private final int state;

    private SSQInfo(final int state) {
        this.state = state;
    }

    @Override
    public void write() {
        writeByte(0x73);
        writeShort(256 + state);
    }
}
