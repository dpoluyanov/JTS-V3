package ru.jts_dev.gameserver.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Service
public class PlayerService {
    private Map<String, GameCharacter> characters = new ConcurrentHashMap<>();

    public GameCharacter getCharacterBy(String connectionId) {
        return characters.get(connectionId);
    }

    @EventListener
    public void characterSelected(CharacterSelectedEvent event) {
        characters.put(event.getConnectionId(), (GameCharacter) event.getSource());
    }

    // TODO: 03.01.16 move to character logout event
    @EventListener
    private void tcpConnectionEventListener(TcpConnectionCloseEvent event) {
        characters.remove(event.getConnectionId());
    }

    public static class CharacterSelectedEvent extends ApplicationEvent {
        private final String connectionId;

        public CharacterSelectedEvent(String connectionId, GameCharacter character) {
            super(character);
            this.connectionId = connectionId;
        }

        public String getConnectionId() {
            return connectionId;
        }
    }
}
