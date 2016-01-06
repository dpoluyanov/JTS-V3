package ru.jts_dev.gameserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author Camelion
 * @since 06.01.16
 */
@ConditionalOnProperty(value = "htm.repository.type", havingValue = "enable")
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    // TODO: 06.01.16 replace with EhCache library
    @Bean
    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Collections.singleton(new ConcurrentMapCache("htm")));

        return cacheManager;
    }
}
