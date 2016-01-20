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

    public void send(String connectionId, OutgoingMessageWrapper message) {
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }

    public void send(GameSession session, OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }

    public void sendToAll(OutgoingMessageWrapper message) {
        sessionService.getSessions().values().forEach(gameSession -> send(gameSession.getConnectionId(), message));
    }
}
