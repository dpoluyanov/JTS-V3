package ru.jts_dev.gameserver.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jts_dev.gameserver.Language;

import java.util.HashSet;
import java.util.Set;

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
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Set<ConcurrentMapCache> caches = new HashSet<>();

        for (Language language : Language.values()) {
            caches.add(new ConcurrentMapCache("htm-" + language.getShortName()));
        }

        cacheManager.setCaches(caches);

        return cacheManager;
    }
}
