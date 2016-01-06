package ru.jts_dev.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Camelion
 * @since 09.12.15
 */
@EnableScheduling
@EntityScan(basePackages = {"ru.jts_dev.common", "ru.jts_dev.gameserver"})
@SpringBootApplication(scanBasePackages = {"ru.jts_dev.common", "ru.jts_dev.gameserver"})
public class GameServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class, args);
    }
}
