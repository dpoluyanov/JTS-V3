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

    /**
     * Random bean should be loaded lazily, and only in points, that it's really needed,
     * because random is instance of {@link ThreadLocalRandom}
     * and if all random beans will be initialized in single thread - no performance impact will be provided.
     * <p>
     * <pre class="code">
     * &#064;Autowired ApplicationContent context;
     * ...
     * Random random = context.getBean(Random.class);
     * ...
     * </pre>
     *
     * @return ThreadLocalRandom for caller thread
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Random random() {
        return ThreadLocalRandom.current();
    }
}
