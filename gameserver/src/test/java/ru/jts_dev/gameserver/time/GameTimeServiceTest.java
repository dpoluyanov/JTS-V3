package ru.jts_dev.gameserver.time;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.GameServerApplication;

/**
 * @author Java-man
 * @since 03.01.2016
 */
// TODO: 04.01.16 Не работает
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GameServerApplication.class)
public class GameTimeServiceTest {

    @Autowired
    private GameTimeService timeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGameTime() throws Exception {

    }
}
