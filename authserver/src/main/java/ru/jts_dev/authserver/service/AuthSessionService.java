package ru.jts_dev.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.util.Encoder;

import java.security.KeyPairGenerator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Camelion
 * @since 07.12.15
 */
@Service
public class AuthSessionService {
    private Map<String, AuthSession> sessions = new ConcurrentHashMap<>();

    private volatile int sessionId = 0;

    @Autowired
    private Random random;

    @Autowired
    private KeyPairGenerator keyPairGenerator;

    @Autowired
    private ApplicationContext context;

    public AuthSession getSessionBy(String connectionId) {
        if (!sessions.containsKey(connectionId))
            throw new NullPointerException("authSession is null for " + connectionId);

        return sessions.get(connectionId);
    }

    private AuthSession createSession(String connectionId) {
        byte[] key = new byte[Encoder.BLOWFISH_KEY_SIZE];
        random.nextBytes(key);
        // TODO: 04.12.15 ++sessionId is possible Integer overflow bug
        return context.getBean(AuthSession.class, connectionId, ++sessionId, keyPairGenerator.generateKeyPair(), key,
                random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    private void tcpConnectionEventListener(TcpConnectionEvent event) {
        sessions.put(event.getConnectionId(), createSession(event.getConnectionId()));
    }

    @EventListener
    private void tcpConnectionEventListener(TcpConnectionCloseEvent event) {
        sessions.remove(event.getConnectionId());
    }
}
