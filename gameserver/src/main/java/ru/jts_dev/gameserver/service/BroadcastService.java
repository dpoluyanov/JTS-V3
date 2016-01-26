package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;

/**
 * @author Java-man
 * @since 19.01.2016
 */
@Service
public class BroadcastService {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private MessageChannel packetChannel;

    public void send(final String connectionId, final OutgoingMessageWrapper message) {
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }

    public void send(final GameSession session, final OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }

    public void sendToAll(final OutgoingMessageWrapper message) {
        sessionService.getSessions().values()
                .parallelStream()
                .forEach(gameSession -> send(gameSession.getConnectionId(), message));
    }
}
