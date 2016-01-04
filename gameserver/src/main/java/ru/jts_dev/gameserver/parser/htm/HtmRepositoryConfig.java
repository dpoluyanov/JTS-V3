package ru.jts_dev.gameserver.parser.htm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Component
@ConfigurationProperties(prefix = "htm.repository")
public class HtmRepositoryConfig {
    private HtmRepositoryType type;

    public HtmRepositoryType getHtmRepositoryType() {
        return type;
    }
}
