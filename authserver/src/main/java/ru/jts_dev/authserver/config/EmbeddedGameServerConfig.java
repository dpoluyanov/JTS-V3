package ru.jts_dev.authserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Camelion
 * @since 10.12.15
 */
@ConditionalOnProperty(prefix = "authserver.gameserver", name = "embedded")
@Configuration
@ComponentScan("ru.jts_dev.gameserver")
public class EmbeddedGameServerConfig {

}
