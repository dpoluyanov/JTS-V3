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

package ru.jts_dev.gameserver.util;

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Component
public class Encoder {
    private final GameSessionService sessionService;

    @Autowired
    public Encoder(GameSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Transformer
    public ByteBuf decrypt(ByteBuf data, @Header(CONNECTION_ID) String connectionId) {
        GameSession gameSession = sessionService.getSessionBy(connectionId);

        assert gameSession != null : "GameSession for " + connectionId + " does not exist";

        ByteBuf key = gameSession.getDecryptKey();

        int temp = 0;
        for (int i = 0; i < data.readableBytes(); i++) {
            final int temp2 = data.getUnsignedByte(i);
            data.setByte(i, (byte) (temp2 ^ key.getByte(i & 15) ^ temp));
            temp = temp2;
        }

        int old = key.getInt(8);
        old += data.readableBytes();

        key.setInt(8, old);

        return data;
    }

    @Transformer
    public ByteBuf encrypt(ByteBuf data, @Header(CONNECTION_ID) String connectionId) {
        GameSession gameSession = sessionService.getSessionBy(connectionId);

        assert gameSession != null : "GameSession for " + connectionId + " does not exist";

        ByteBuf key = gameSession.getEncryptKey();

        int temp = 0;
        for (int i = 0; i < data.readableBytes(); i++) {
            int temp2 = data.getUnsignedByte(data.readerIndex() + i);
            temp = temp2 ^ key.getByte(i & 15) ^ temp;
            data.setByte(data.readerIndex() + i, (byte) temp);
        }

        int old = key.getInt(8);
        old += data.readableBytes();

        key.setInt(8, old);

        return data;
    }
}
