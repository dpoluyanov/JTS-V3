package ru.jts_dev.gameserver.parser.html;

import com.neovisionaries.i18n.LanguageCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.gameserver.config.CacheConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@SpringJUnitConfig({HtmlRepositoryConfig.class, HtmlRepository.class, CacheConfig.class})
public class HtmlRepositoryTest {
    @Autowired
    private HtmlRepository htmlRepository;

    @Test
    public void testGetHtml() throws Exception {
        String content = htmlRepository.getHtml(LanguageCode.en, "abel001.htm");
        assertThat(content).hasSize(512);

        content = htmlRepository.getHtml(LanguageCode.en, "abel002.htm");
        assertThat(content).hasSize(367);
    }
}
