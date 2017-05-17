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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterSelected;
import ru.jts_dev.gameserver.packets.out.SSQInfo;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService.CharacterSelectedEvent;
import ru.jts_dev.gameserver.time.GameTimeService;

import java.util.List;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Opcode(0x12)
public class CharacterSelect extends IncomingMessageWrapper {
    private static final Logger logger = LoggerFactory.getLogger(CharacterSelect.class);
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private GameTimeService timeService;

    @Autowired
    private GameCharacterRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    private int characterIndex;
    private int unk1;
    private int x;
    private int y;
    private int z;

    @Override
    public void prepare() {
        characterIndex = readInt();
        unk1 = readShort();
        if (unk1 > 0) {
            x = readInt();
            y = readInt();
            z = readInt();
        }
    }

    @Override
    public void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());

        final String account = sessionService.getAccountBy(getConnectionId());
        final List<GameCharacter> characters = repository.findAllByAccountName(account);

        if (characterIndex < 0 || characters.isEmpty() || characters.size() <= characterIndex) {
            sessionService.forcedClose(session);
            return;
        }

        logger.debug(toString());

        final GameCharacter character = characters.get(characterIndex);

        publisher.publishEvent(new CharacterSelectedEvent(getConnectionId(), character));

        broadcastService.send(session, SSQInfo.NOTHING);

        final int minutesPassed = (int) timeService.minutesPassedSinceDayBeginning();
        broadcastService.send(session, new CharacterSelected(character, session.getPlayKey(), minutesPassed));
    }

    @Override
    public String toString() {
        return "CharacterSelect{" +
                "characterIndex=" + characterIndex +
                ", unk1=" + unk1 +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
