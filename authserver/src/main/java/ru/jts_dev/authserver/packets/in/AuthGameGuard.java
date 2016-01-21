package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.packets.out.GGAuth;
import ru.jts_dev.authserver.packets.out.LoginFail;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.service.BroadcastService;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.authserver.packets.out.LoginFail.REASON_ACCESS_FAILED;

/**
 * @author Camelion
 * @since 06.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class AuthGameGuard extends IncomingMessageWrapper {

    @Autowired
    private AuthSessionService authSessionService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private AbstractConnectionFactory connectionFactory;

    private int sessionId;

    @Override
    public void prepare() {
        sessionId = readInt();
    }

    @Override
    public void run() {
        AuthSession session = authSessionService.getSessionBy(getConnectionId());

        if (sessionId == session.getSessionId()) {
            broadcastService.send(session, new GGAuth(sessionId));
        } else {// TODO: 11.12.15 send login fail packet
            broadcastService.send(session, new LoginFail(REASON_ACCESS_FAILED));
            connectionFactory.closeConnection(getConnectionId());
        }
    }
}
