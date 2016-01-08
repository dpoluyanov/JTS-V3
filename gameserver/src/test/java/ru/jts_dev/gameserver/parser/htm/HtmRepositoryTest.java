package ru.jts_dev.gameserver.parser.htm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.Language;
import ru.jts_dev.gameserver.config.CacheConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HtmRepositoryConfig.class, HtmRepository.class, CacheConfig.class})
//@SpringApplicationConfiguration(classes = GameServerApplication.class)
public class HtmRepositoryTest {
    @Autowired
    private HtmRepository htmRepository;

    @Test
    public void testGetHtm() throws Exception {
        String content = htmRepository.getHtm(Language.ENGLISH, "abel001.htm");
        assertThat(content, not(isEmptyOrNullString()));

        content = htmRepository.getHtm(Language.ENGLISH, "abel002.htm");
        assertThat(content, not(isEmptyOrNullString()));
    }
}
