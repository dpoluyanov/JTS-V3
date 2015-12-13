package ru.jts_dev.authserver.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Camelion
 * @since 10.12.15
 */
@ConditionalOnProperty("authserver.gameserver.embedded")
@Configuration
public class EmbeddedGameServerConfig {
    @Bean
    public SpringApplicationBuilder gameserverAppBuilder() throws ClassNotFoundException {
        return new SpringApplicationBuilder(Class.forName("ru.jts_dev.gameserver.GameServerApplication"))
                .bannerMode(Banner.Mode.OFF)
                .main(Class.forName("ru.jts_dev.gameserver.GameServerApplication"))
                .profiles("embedded-gs");
    }
}
