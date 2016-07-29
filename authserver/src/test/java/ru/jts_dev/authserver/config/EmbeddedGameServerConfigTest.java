package ru.jts_dev.authserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Camelion
 * @since 10.12.15
 */
@SpringJUnitConfig(EmbeddedGameServerConfig.class)
@TestPropertySource(properties = "authserver.gameserver.embedded = false")
public class EmbeddedGameServerConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DirtiesContext
    public void test() {
        assertThat(context.containsBean("embeddedGameServerConfig"));
    }

    @TestPropertySource(properties = "authserver.gameserver.embedded = true")
    @SpringJUnitConfig(EmbeddedGameServerConfig.class)
    public static class EmbeddedGameServerConfigMatched {

        @Autowired
        private ApplicationContext context;

        @Test
        @DirtiesContext
        public void test() {
            assertThat(context.containsBean("embeddedGameServerConfig"));
        }
    }
}