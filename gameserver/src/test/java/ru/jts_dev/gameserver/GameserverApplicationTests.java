package ru.jts_dev.gameserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.parser.impl.PcParametersHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameServerApplication.class)
public class GameserverApplicationTests {

	@Autowired
	private PcParametersHolder PcParametersHolder;

	@Test
	public void contextLoads() {
	}
}
