package ru.jts_dev.gameserver.config;

import io.netty.util.HashedWheelTimer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Java-man
 * @since 27.12.2015
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        return scheduler;
    }

    @Bean
    public HashedWheelTimer hashedWheelTimer() {
        HashedWheelTimer timer = new HashedWheelTimer();
        return timer;
    }
}
