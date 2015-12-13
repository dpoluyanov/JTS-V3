package ru.jts_dev.gameserver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.OutgoingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class GameSession {
    private final String connectionId;
    private final byte[] key;
    @Autowired
    private MessageChannel packetChannel;

    public GameSession(String connectionId, byte[] key) {
        if (key.length != 16)
            throw new RuntimeException("key must be multiply by 16");

        this.connectionId = connectionId;
        this.key = key;
    }

    public void send(OutgoingMessageWrapper msg) {
        msg.getHeaders().put(IpHeaders.CONNECTION_ID, connectionId);
        packetChannel.send(msg);
    }
}
