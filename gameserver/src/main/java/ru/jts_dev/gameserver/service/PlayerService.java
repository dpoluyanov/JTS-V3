package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Service
public class PlayerService {
    private final Map<String, GameCharacter> characters = new ConcurrentHashMap<>();
    @Autowired
    private GameCharacterRepository gameCharacterRepository;

    public final GameCharacter getCharacterBy(final String connectionId) {
        return characters.get(connectionId);
    }

    @EventListener
    public final void characterSelected(final CharacterSelectedEvent event) {
        characters.put(event.getConnectionId(), (GameCharacter) event.getSource());
    }

    // TODO: 03.01.16 move to character logout event
    @EventListener
    private void tcpConnectionEventListener(final TcpConnectionCloseEvent event) {
        final GameCharacter character = characters.remove(event.getConnectionId());
        if (character != null) {
            gameCharacterRepository.save(character);
        }
    }

    @PreDestroy
    private void destroy() {
        gameCharacterRepository.save(characters.values());
    }

    public static class CharacterSelectedEvent extends ApplicationEvent {
        private static final long serialVersionUID = 2145294139798098206L;
        private final String connectionId;

        public CharacterSelectedEvent(final String connectionId, final GameCharacter character) {
            super(character);
            this.connectionId = connectionId;
        }

        public final String getConnectionId() {
            return connectionId;
        }
    }
}
