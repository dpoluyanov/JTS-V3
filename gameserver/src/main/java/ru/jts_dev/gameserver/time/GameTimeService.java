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

package ru.jts_dev.gameserver.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.repository.ServerVariablesRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.time.events.DayNightStateChanged;
import ru.jts_dev.gameserver.variables.server.ServerVariable;
import ru.jts_dev.gameserver.variables.server.ServerVariableKey;
import ru.jts_dev.gameserver.variables.server.ServerVariableType;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static ru.jts_dev.gameserver.time.GameTimeConstants.MIN_DATE_TIME;

/**
 * 4 hours in RL = 1 Day in L2
 * 1 hour in RL = 6 hours in L2 (Night lasts 6 hours)
 * 1 buff round (20 min RL) = 2 hours in L2
 * 10 min in RL = 1 hour in L2
 * 1 min in RL = 6 min in L2
 * 10 seconds in RL = 1 min in L2
 *
 * @author Java-man
 * @since 03.01.2016
 */
@Service
public class GameTimeService {
    private static final int SUNRISE_HOUR = 6; // час, в который встает солнце
    private static final int SUNSET_HOUR = 24; // час, в который садится солнце

    private final Logger logger = LoggerFactory.getLogger(GameTimeService.class);

    private final GameServerConfig gameServerConfig;
    private final BroadcastService broadcastService;
    private final ServerVariablesRepository serverVariablesRepository;
    private final ApplicationEventPublisher eventPublisher;

    private ZonedDateTime dateTime = MIN_DATE_TIME;

    @Autowired
    public GameTimeService(final GameServerConfig gameServerConfig, final BroadcastService broadcastService,
                           final ServerVariablesRepository serverVariablesRepository, final ApplicationEventPublisher eventPublisher) {
        this.gameServerConfig = gameServerConfig;
        this.broadcastService = broadcastService;
        this.serverVariablesRepository = serverVariablesRepository;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    private void loadDateTime() {
        final ServerVariableKey key = new ServerVariableKey(gameServerConfig.getServerId(), ServerVariableType.SERVER_TIME);
        final ServerVariable serverTime = serverVariablesRepository.findOne(key);

        if (serverTime != null) {
            final String value = serverTime.getValue();
            dateTime = ZonedDateTime.parse(value);
        }

        logger.info("Current in-game time is {}.", dateTime);
    }

    @Scheduled(initialDelay = 10_000, fixedRate = 10_000)
    private void updateGameClock() {
        final int oldHour = dateTime.getHour();
        final int oldDay = dateTime.getDayOfYear();
        final boolean wasDay = isNowDay();

        dateTime = dateTime.plusMinutes(1L);

        saveNewGameTimeInDatabase();

        final int newHour = dateTime.getHour();

        if (oldHour != newHour) {
            // update time for all players
            final long minutesPassed = minutesPassed();
            broadcastService.sendToAll(new ClientSetTime((int) minutesPassed));

            final boolean nowDay = isNowDay();

            // check if day night state changed
            if (wasDay != nowDay) {
                eventPublisher.publishEvent(new DayNightStateChanged(nowDay));
            }

            final int newDay = dateTime.getDayOfYear();

            // check if a whole nowDay passed
            if (oldDay != newDay)
                logger.info("An in-game day passed - it's now: {}", dateTime);
        }
    }

    public long minutesPassed() {
        return MIN_DATE_TIME.until(dateTime, ChronoUnit.MINUTES);
    }

    public long minutesPassedSinceDayBeginning() {
        final ZonedDateTime dayBeginning = dateTime.with(LocalTime.MIN);
        return dayBeginning.until(dateTime, ChronoUnit.MINUTES);
    }

    public boolean isNowDay() {
        final int hour = dateTime.getHour();
        return hour >= SUNRISE_HOUR || hour < SUNSET_HOUR;
    }

    private void saveNewGameTimeInDatabase() {
        final ServerVariableKey key = new ServerVariableKey(gameServerConfig.getServerId(), ServerVariableType.SERVER_TIME);
        ServerVariable serverTime = serverVariablesRepository.findOne(key);

        if (serverTime == null) {
            serverTime = new ServerVariable();
            serverTime.setServerId(gameServerConfig.getServerId());
            serverTime.setServerVariableType(ServerVariableType.SERVER_TIME);
        }

        final String dateTimeStr = dateTime.toString();
        serverTime.setValue(dateTimeStr);

        serverVariablesRepository.save(serverTime);
    }
}
