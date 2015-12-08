package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.service.SessionService;
import ru.jts_dev.authserver.model.GameSession;
import ru.jts_dev.authserver.packets.IncomingMessageWrapper;
import ru.jts_dev.authserver.packets.out.GGAuth;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 06.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class AuthGameGuard extends IncomingMessageWrapper {

    @Autowired
    private SessionService sessionService;

    private int sessionId;

    @Override
    public void prepare() {
        sessionId = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        if (sessionId == session.getSessionId())
            session.send(new GGAuth(sessionId));
        else
            session.send(null);
    }
}
