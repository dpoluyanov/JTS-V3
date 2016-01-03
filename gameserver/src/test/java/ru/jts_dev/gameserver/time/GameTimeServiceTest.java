package ru.jts_dev.gameserver.time;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Java-man
 * @since 03.01.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GameTimeService.class)
public class GameTimeServiceTest {
    @Autowired
    private GameTimeService timeService;

    @Test
    public void testGameTime() throws Exception {

    }
}
