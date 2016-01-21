package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.packets.out.LoginFail;
import ru.jts_dev.authserver.packets.out.ServerList;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.service.BroadcastService;
import ru.jts_dev.authserver.service.GameServerService;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.authserver.packets.out.LoginFail.REASON_ACCESS_FAILED;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestServerList extends IncomingMessageWrapper {
    private int loginKey1, loginKey2;

    @Autowired
    private AuthSessionService authSessionService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private GameServerService gameServerService;

    @Autowired
    private AbstractConnectionFactory connectionFactory;

    @Override
    public void prepare() {
        loginKey1 = readInt();
        loginKey2 = readInt();
    }

    @Override
    public void run() {
        AuthSession session = authSessionService.getSessionBy(getConnectionId());

        if (session.getLoginKey1() == loginKey1 && session.getLoginKey2() == loginKey2) {
            broadcastService.send(session, new ServerList(gameServerService.getGameServers()));
        } else {
            broadcastService.send(session, new LoginFail(REASON_ACCESS_FAILED));
            connectionFactory.closeConnection(getConnectionId());
        }
    }
}
