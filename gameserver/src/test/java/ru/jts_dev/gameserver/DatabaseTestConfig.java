package ru.jts_dev.gameserver;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Camelion
 * @since 28.12.15
 */
@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EntityScan(basePackages = "ru.jts_dev.gameserver.model")
public class DatabaseTestConfig {

}
