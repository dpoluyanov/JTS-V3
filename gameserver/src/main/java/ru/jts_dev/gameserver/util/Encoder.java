package ru.jts_dev.gameserver.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.service.GameSessionService;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Component
public class Encoder {
    @Autowired
    private GameSessionService sessionService;

    @Transformer
    public void encode(byte[] data, @Header String conenctionId) {

    }
}
