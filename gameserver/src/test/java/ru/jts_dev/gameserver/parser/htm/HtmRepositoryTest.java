package ru.jts_dev.gameserver.parser.htm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@ContextConfiguration(classes = {HtmRepository.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class HtmRepositoryTest {
    @Autowired
    private HtmRepository htmRepository;

    @Test
    public void testGetHtm() throws Exception {
        Path path = Paths.get("htm-en" + "/" + "abel001.htm");

        String content = htmRepository.getHtm(path.toString());

        assertThat(content, is(not(isEmptyOrNullString())));
    }
}
