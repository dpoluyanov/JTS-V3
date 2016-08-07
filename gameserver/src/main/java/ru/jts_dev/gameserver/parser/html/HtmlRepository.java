package ru.jts_dev.gameserver.parser.html;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressorStatistics;
import com.neovisionaries.i18n.LanguageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Component
public final class HtmlRepository {
    private final Logger logger = LoggerFactory.getLogger(HtmlRepository.class);

    private final HtmlRepositoryConfig config;
    private final HtmlCompressor htmlCompressor;
    private final ResourceLoader resourceLoader;

    private Path htmlDir;

    @Autowired
    public HtmlRepository(HtmlRepositoryConfig config, HtmlCompressor htmlCompressor, ResourceLoader resourceLoader) {
        this.config = config;
        this.htmlCompressor = htmlCompressor;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void loadHtm() throws Exception {
        htmlDir = Paths.get(resourceLoader.getResource("html").getURI());
        Map<LanguageCode, Path> htmlToLoad = config.getHtmlRepositoryType().htmlToLoad(resourceLoader, htmlDir);
        htmlToLoad.entrySet().forEach(entry -> getHtml(entry.getKey(), entry.getValue().toString()));
    }

    @Cacheable(cacheNames = "html", key = "#language.toString().concat('\').concat(#htmlName)")
    public String getHtml(LanguageCode language, String htmlName) {
        String html = readHtml(language, htmlName);
        html = compressHtml(htmlCompressor, html);

        logger.debug("Loaded {} {}: {}", language, htmlName, html);
        return html;
    }

    private String readHtml(LanguageCode language, String htmlName) {
        Path htmlPath = Paths.get(htmlDir + "\\" + language + "\\" + htmlName);
        if (!Files.exists(htmlPath)) {
            throw new IllegalArgumentException("Can't find html: " + htmlPath);
        }
        try {
            byte[] content = Files.readAllBytes(htmlPath);
            String html = new String(content, UTF_8);
            return html;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String compressHtml(HtmlCompressor compressor, String content) {
        String result = compressor.compress(content);

        HtmlCompressorStatistics statistics = compressor.getStatistics();
        logger.debug(String.format("Compression time: %,d ms, Original size: %,d bytes, Compressed size: %,d bytes",
                statistics.getTime(), statistics.getOriginalMetrics().getFilesize(),
                statistics.getCompressedMetrics().getFilesize()));

        return result;
    }
}
