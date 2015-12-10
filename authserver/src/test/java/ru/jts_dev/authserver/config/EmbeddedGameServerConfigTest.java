package ru.jts_dev.authserver.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Camelion
 * @since 10.12.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        EmbeddedGameServerConfig.class
})
@TestPropertySource(properties = "authserver.gameserver.embedded = false")
public class EmbeddedGameServerConfigTest extends Assert {

    @Autowired
    private ApplicationContext context;

    @Test
    @DirtiesContext
    public void test() {
        assertFalse(context.containsBean("embeddedGameServerConfig"));
    }

    @TestPropertySource(properties = "authserver.gameserver.embedded = true")
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = {
            EmbeddedGameServerConfig.class
    })
    public static class EmbeddedGameServerConfigMatched {

        @Autowired
        private ApplicationContext context;

        @Test
        @DirtiesContext
        public void test() {
            assertTrue(context.containsBean("embeddedGameServerConfig"));
        }
    }
}