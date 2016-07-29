package ru.jts_dev.gameserver.parser.htm;

import org.assertj.core.api.Condition;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.Language;
import ru.jts_dev.gameserver.config.CacheConfig;
import ru.jts_dev.gameserver.parser.data.npc.NpcDatasHolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@SpringJUnitConfig({HtmRepositoryConfig.class, HtmRepository.class, CacheConfig.class})
public class HtmRepositoryTest {
    @Autowired
    private HtmRepository htmRepository;

    @Test
    public void testGetHtm() throws Exception {
        String content = htmRepository.getHtm(Language.ENGLISH, "abel001.htm");
        assertThat(content).isNotNull().isNotEmpty();

        content = htmRepository.getHtm(Language.ENGLISH, "abel002.htm");
        assertThat(content).isNotNull().isNotEmpty();
    }
}
