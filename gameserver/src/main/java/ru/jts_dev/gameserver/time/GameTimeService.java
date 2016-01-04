package ru.jts_dev.gameserver.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.config.GameServerConfig;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.variables.server.ServerVariable;
import ru.jts_dev.gameserver.variables.server.ServerVariableKey;
import ru.jts_dev.gameserver.variables.server.ServerVariableType;
import ru.jts_dev.gameserver.variables.server.ServerVariablesRepository;

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
    private final GameServerConfig gameServerConfig;
    private final GameSessionService gameSessionService;
    private final ServerVariablesRepository serverVariablesRepository;

    private ZonedDateTime dateTime = MIN_DATE_TIME;

    @Autowired
    public GameTimeService(GameServerConfig gameServerConfig, GameSessionService gameSessionService,
                           ServerVariablesRepository serverVariablesRepository) {
        this.gameServerConfig = gameServerConfig;
        this.gameSessionService = gameSessionService;
        this.serverVariablesRepository = serverVariablesRepository;
    }

    @PostConstruct
    private void loadDateTime() {
        ServerVariableKey key = new ServerVariableKey(gameServerConfig.getServerId(), ServerVariableType.SERVER_TIME);
        ServerVariable serverTime = serverVariablesRepository.findOne(key);

        if (serverTime != null) {
            String value = serverTime.getValue();
            dateTime = ZonedDateTime.parse(value);
        }
    }

    @Scheduled(initialDelay = 10_000, fixedRate = 10_000)
    private void updateGameClock() {
        int oldHour = dateTime.getHour();

        dateTime = dateTime.plusMinutes(1);

        saveNewGameTimeInDatabase();

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
