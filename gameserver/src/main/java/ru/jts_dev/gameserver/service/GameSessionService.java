package ru.jts_dev.gameserver.service;

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameSession;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.buffer.Unpooled.buffer;
import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Service
public class GameSessionService {
    private static final byte[] STATIC_KEY_PART = new byte[]{
            (byte) 0xc8, (byte) 0x27, (byte) 0x93, (byte) 0x01, (byte) 0xa1, (byte) 0x6c, (byte) 0x31, (byte) 0x97
    };

    private Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Random random;

    public GameSession getSessionBy(String connectionId) {
        if (!sessions.containsKey(connectionId))
            throw new NullPointerException("gameSession is null for " + connectionId);

        return sessions.get(connectionId);
    }

    private GameSession createSession(String connectionId) {
        ByteBuf encryptKey = buffer(16, 16).order(ByteOrder.LITTLE_ENDIAN);
        // randomize first 8 bytes of key
        random.nextBytes(encryptKey.array());
        encryptKey.writerIndex(8);

        // and add 8 byte as static key part
        encryptKey.writeBytes(STATIC_KEY_PART);

        // then copy encrypt key to decrypt key
        ByteBuf decryptKey = copiedBuffer(encryptKey).order(ByteOrder.LITTLE_ENDIAN);

        // and pass keys to a game session object
        return context.getBean(GameSession.class, connectionId, encryptKey, decryptKey);
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
