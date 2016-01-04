package ru.jts_dev.gameserver.time;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.config.GameServerConfig;

/**
 * @author Java-man
 * @since 03.01.2016
 */
// TODO: 04.01.16 Не работает
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GameTimeService.class})
public class GameTimeServiceTest {
    @Autowired
    private GameTimeService timeService;

    @Test
    public void testGameTime() throws Exception {

    }
}
