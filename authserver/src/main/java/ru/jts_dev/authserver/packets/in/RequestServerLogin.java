package ru.jts_dev.authserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.authserver.packets.out.LoginFail;
import ru.jts_dev.authserver.packets.out.PlayOk;
import ru.jts_dev.authserver.service.AuthSessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.authserver.packets.out.LoginFail.REASON_ACCESS_FAILED;

/**
 * @author Camelion
 * @since 11.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class RequestServerLogin extends IncomingMessageWrapper {
    private int key1;
    private int key2;
    private byte serverId;

    @Autowired
    private AuthSessionService authSessionService;

    @Autowired
    private AbstractConnectionFactory connectionFactory;

    @Override
    public void prepare() {
        key1 = readInt();
        key2 = readInt();
        serverId = readByte();
    }

    @Override
    public void run() {
        AuthSession session = authSessionService.getSessionBy(getConnectionId());

        if (session.getLoginKey1() == key1 && session.getLoginKey2() == key2) {
            session.send(new PlayOk(session.getGameKey1(), session.getGameKey2()));
        } else {
            session.send(new LoginFail(REASON_ACCESS_FAILED));
        }
        // TODO: 11.12.15 не работает, закрывает соединение раньше, чем приходит пакет
        // connectionFactory.closeConnection(getConnectionId());
    }
}
