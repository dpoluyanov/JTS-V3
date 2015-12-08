package ru.jts_dev.authserver.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.jts_dev.authserver.model.GameServerSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Camelion
 * @since 09.12.15
 */
@Lazy
@Service
public class GameServerService {
    private List<GameServerSession> gameServers = new ArrayList<>();

    public List<GameServerSession> getGameServers() {
        return Collections.unmodifiableList(gameServers);
    }
}
