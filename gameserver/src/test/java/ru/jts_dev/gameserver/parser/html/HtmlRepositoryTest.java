package ru.jts_dev.gameserver.parser.html;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.gameserver.config.CacheConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@EnableConfigurationProperties(HtmlRepositoryConfig.class)
@SpringJUnitConfig(classes = {HtmlRepository.class, CacheConfig.class})
@TestPropertySource(properties = {"gameserver.html.repository.type=lazy"})
public class HtmlRepositoryTest {
    @Autowired
    private HtmlRepository htmlRepository;

    @Test
    public void testGetHtml() throws Exception {
        String content = htmlRepository.getHtml(Locale.ENGLISH, "abel001.htm");
        assertThat(content).hasSize(512);

        content = htmlRepository.getHtml(Locale.ENGLISH, "abel002.htm");
        assertThat(content).hasSize(367);
    }
}
