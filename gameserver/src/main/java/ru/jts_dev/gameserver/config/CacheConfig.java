package ru.jts_dev.gameserver.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Camelion
 * @since 06.01.16
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    // TODO: 06.01.16 replace with EhCache library
    @Bean
    @Override
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("html");
    }
}
