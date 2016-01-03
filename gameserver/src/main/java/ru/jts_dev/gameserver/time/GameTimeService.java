package ru.jts_dev.gameserver.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.service.GameSessionService;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

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
@Component
public class GameTimeService {
    private final GameSessionService gameSessionService;

    private ZonedDateTime dateTime = MIN_DATE_TIME;

    @Autowired
    public GameTimeService(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostConstruct
    private void loadDateTime() {
        // TODO load from db
    }

    @Scheduled(fixedDelay = 10_000, fixedRate = 10_000)
    private void updateGameClock() {
        int oldHour = dateTime.getHour();

        dateTime = dateTime.plusMinutes(1);
        // TODO save in db new time

        int newHour = dateTime.getHour();

        if (oldHour != newHour) {
            // update time for all players
            Collection<GameSession> sessions = gameSessionService.getSessions();
            long gameTimeInMinutes = getGameTimeInMinutes();
            sessions.forEach(gameSession -> gameSession.send(new ClientSetTime(gameTimeInMinutes)));

            // TODO night, day listeners
        }
    }

    public long getGameTimeInMinutes() {
        return MIN_DATE_TIME.until(dateTime, ChronoUnit.MINUTES);
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}
