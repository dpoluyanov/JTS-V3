package ru.jts_dev.gameserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.Exceptions.ThrowingFunction;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.common.packets.StaticOutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;

import java.util.stream.Stream;

/**
 * @author Java-man
 * @since 19.01.2016
 */
@Service
public class BroadcastService {
    private static final int THRESHOLD = 25;
    private static final Logger logger = LoggerFactory.getLogger(BroadcastService.class);

    private final GameSessionService sessionService;
    private final MessageChannel packetChannel;

    @Autowired
    public BroadcastService(MessageChannel packetChannel, GameSessionService sessionService) {
        this.packetChannel = packetChannel;
        this.sessionService = sessionService;
    }

    public final void sendToAll(final OutgoingMessageWrapper message) {
        Stream<GameSession> stream = sessionService.getSessions().size() > THRESHOLD ?
                sessionService.getSessions().values().parallelStream() : sessionService.getSessions().values().stream();
        stream.forEach(gameSession -> send(gameSession.getConnectionId(), message));
    }

    public final void send(final GameSession session, final OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }

    public final void send(final String connectionId, OutgoingMessageWrapper message) {
        if (message.isStatic() && message instanceof StaticOutgoingMessageWrapper) {
            send(connectionId, message);
            logger.trace("Clone {} packet", message.getClass().getSimpleName());
            return;
        }
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }
}
