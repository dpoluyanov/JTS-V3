package ru.jts_dev.gameserver.parser.htm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.model.Language;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@ContextConfiguration(classes = {HtmRepositoryConfig.class, HtmRepository.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class HtmRepositoryTest {
    @Autowired
    private HtmRepository htmRepository;

    @Test
    public void testGetHtm() throws Exception {
        Path path = Paths.get("abel001.htm");
        String content = htmRepository.getHtm(Language.ENGLISH, "abel001.htm");
        assertThat(content, is(not(isEmptyOrNullString())));

        path = Paths.get("abel002.htm");
        content = htmRepository.getHtm(Language.ENGLISH, "abel002.htm");
        assertThat(content, is(not(isEmptyOrNullString())));
    }
}
