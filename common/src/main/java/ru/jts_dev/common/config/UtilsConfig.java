package ru.jts_dev.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 02.12.15
 */
@Configuration
public class UtilsConfig {
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Random random() {
        return ThreadLocalRandom.current();
    }
}
