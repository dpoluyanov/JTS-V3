package ru.jts_dev.authserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.messaging.GameServerInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Camelion
 * @since 09.12.15
 */
@Lazy
@Service
public class GameServerService {
    private static final Logger log = LoggerFactory.getLogger(GameServerService.class);
    private Set<GameServerInfo> gameServers = new HashSet<>();

    public Set<GameServerInfo> getGameServers() {
        return Collections.unmodifiableSet(gameServers);
    }

    @JmsListener(destination = "gameServersQueue")
    public void processGameServerInfo(GameServerInfo gameServerInfo) {
        if (!gameServers.contains(gameServerInfo))
            log.info("Connected new GameServer with id: "
                    + gameServerInfo.getServerId() + " from: " + gameServerInfo.getAddress());

        gameServers.add(gameServerInfo);
    }
}
