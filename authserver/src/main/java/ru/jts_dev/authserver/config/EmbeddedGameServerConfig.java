package ru.jts_dev.authserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Camelion
 * @since 10.12.15
 */
@ConditionalOnProperty("authserver.gameserver.embedded")
@Configuration
@ComponentScan("ru.jts_dev.gameserver")
@PropertySource("classpath:application-embedded-gs.properties")
@PropertySource(value = "file:application-embedded-gs.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:/config/application-embedded-gs.properties", ignoreResourceNotFound = true)
public class EmbeddedGameServerConfig {

}
