package ru.jts_dev.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Java-man
 * @since 19.01.2016
 */
@Service
public class BroadcastService {
    private final MessageChannel packetChannel;

    @Autowired
    public BroadcastService(MessageChannel packetChannel) {
        this.packetChannel = packetChannel;
    }

    private void send(String connectionId, OutgoingMessageWrapper message) {
        message.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(message);
    }

    public void send(AuthSession session, OutgoingMessageWrapper message) {
        send(session.getConnectionId(), message);
    }
}
