package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.GameSession;
import ru.jts_dev.authserver.packets.IncomingMessageWrapper;
import ru.jts_dev.authserver.packets.out.ServerList;
import ru.jts_dev.authserver.service.GameServerService;
import ru.jts_dev.authserver.service.SessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class RequestServerList extends IncomingMessageWrapper {
    private int loginKey1, loginKey2;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private GameServerService gameServerService;

    @Override
    public void prepare() {
        loginKey1 = readInt();
        loginKey2 = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        if (session.getLoginKey1() == loginKey1 && session.getLoginKey2() == loginKey2) {
            session.send(new ServerList(gameServerService.getGameServers()));
        } else {
            // TODO: 09.12.15 close connection
            session.send(null);
        }
    }
}
