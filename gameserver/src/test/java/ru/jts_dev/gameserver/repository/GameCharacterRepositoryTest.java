package ru.jts_dev.gameserver.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Camelion
 * @since 28.12.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@ContextConfiguration(classes = GameCharacterRepository.class)
public class GameCharacterRepositoryTest {

    @Test
    public void testUpdateLastUsed() throws Exception {

    }
}