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
import ru.jts_dev.gameserver.service.BroadcastServiceTemp;
import ru.jts_dev.gameserver.time.events.DayNightStateChanged;
import ru.jts_dev.gameserver.variables.server.ServerVariable;
import ru.jts_dev.gameserver.variables.server.ServerVariableKey;
import ru.jts_dev.gameserver.variables.server.ServerVariableType;

import javax.annotation.PostConstruct;
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
    private final BroadcastServiceTemp broadcastService;
    private final ServerVariablesRepository serverVariablesRepository;
    private final ApplicationEventPublisher eventPublisher;

    private ZonedDateTime dateTime = MIN_DATE_TIME;

    @Autowired
    public GameTimeService(GameServerConfig gameServerConfig, BroadcastServiceTemp broadcastService,
                           ServerVariablesRepository serverVariablesRepository, ApplicationEventPublisher eventPublisher) {
        this.gameServerConfig = gameServerConfig;
        this.broadcastService = broadcastService;
        this.serverVariablesRepository = serverVariablesRepository;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    private void loadDateTime() {
        ServerVariableKey key = new ServerVariableKey(gameServerConfig.getServerId(), ServerVariableType.SERVER_TIME);
        ServerVariable serverTime = serverVariablesRepository.findOne(key);

        if (serverTime != null) {
            String value = serverTime.getValue();
            dateTime = ZonedDateTime.parse(value);
        }

        logger.info("Current time is {}.", dateTime);
    }

    @Scheduled(initialDelay = 10_000, fixedRate = 10_000)
    private void updateGameClock() {
        int oldHour = dateTime.getHour();
        int oldDay = dateTime.getDayOfYear();
        boolean wasDay = isNowDay();

        dateTime = dateTime.plusMinutes(1L);

        saveNewGameTimeInDatabase();

        int newHour = dateTime.getHour();

        if (oldHour != newHour) {
            // update time for all players
            long gameTimeInMinutes = getGameTimeInMinutes();
            broadcastService.sendToAll(new ClientSetTime(gameTimeInMinutes));

            boolean nowDay = isNowDay();

            // check if day night state changed
            if (wasDay != nowDay) {
                eventPublisher.publishEvent(new DayNightStateChanged(nowDay));
            }

            int newDay = dateTime.getDayOfYear();

            // check if a whole nowDay passed
            if (oldDay != newDay)
                logger.info("An in-game day passed - it's now: {}", dateTime);
        }
    }

    public long getGameTimeInMinutes() {
        return MIN_DATE_TIME.until(dateTime, ChronoUnit.MINUTES);
    }

    public boolean isNowDay() {
        int hour = dateTime.getHour();
        return hour >= SUNRISE_HOUR || hour < SUNSET_HOUR;
    }

    private void saveNewGameTimeInDatabase() {
        ServerVariableKey key = new ServerVariableKey(gameServerConfig.getServerId(), ServerVariableType.SERVER_TIME);
        ServerVariable serverTime = serverVariablesRepository.findOne(key);

        if (serverTime == null) {
            serverTime = new ServerVariable();
            serverTime.setServerId(gameServerConfig.getServerId());
            serverTime.setServerVariableType(ServerVariableType.SERVER_TIME);
        }

        String dateTimeStr = dateTime.toString();
        serverTime.setValue(dateTimeStr);

        serverVariablesRepository.save(serverTime);
    }
}
