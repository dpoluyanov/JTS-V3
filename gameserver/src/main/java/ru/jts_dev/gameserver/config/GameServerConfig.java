package ru.jts_dev.gameserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Configuration
public class GameServerConfig {
    @Value("${gameserver.id}")
    private byte id;

    @Value("${gameserver.host}")
    private String host;

    @Value("${gameserver.port}")
    private int port;

    @Value("${gameserver.character.creation.disabled}")
    private boolean charCreationDisabled;

    public byte getServerId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isCharCreationDisabled() {
        return charCreationDisabled;
    }
}
