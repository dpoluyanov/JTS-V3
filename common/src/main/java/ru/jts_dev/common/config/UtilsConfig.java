package ru.jts_dev.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Camelion
 * @since 02.12.15
 */
@Configuration
public class UtilsConfig {
    @Bean
    @Lazy
    public Random random() {
        return ThreadLocalRandom.current();
    }
}
