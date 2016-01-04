package ru.jts_dev.gameserver.parser.htm;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.Language;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Component
public class HtmRepository {
    private final Logger logger = LoggerFactory.getLogger(HtmRepository.class);

    private final HtmRepositoryConfig config;
    private final HtmlCompressor htmlCompressor;

    @Autowired
    public HtmRepository(HtmRepositoryConfig config, HtmlCompressor htmlCompressor) {
        this.config = config;
        this.htmlCompressor = htmlCompressor;
    }

    @PostConstruct
    private void loadHtm() {
        if (config.getHtmRepositoryType() != HtmRepositoryType.ENABLE)
            return;

        for (Language language : Language.values()) {
            Path htmPath = Paths.get("htm-" + language.getShortName());
            try {
                Files.walk(htmPath).forEach(path -> getHtm(path.toString()));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public String getHtm(String htmPath) {
        if (config.getHtmRepositoryType() == HtmRepositoryType.DISABLE)
            return readHtm(htmPath);
        else
            return getCachedHtm(htmPath);
    }

    @Cacheable("htm")
    private String getCachedHtm(String htmPath) {
        String htm = readHtm(htmPath);
        htm = compressHtm(htmlCompressor, htm);
        return htm;
    }

    private String readHtm(String htmPath) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(htmPath).toURI());

            byte[] content = Files.readAllBytes(path);
            String htm = new String(content, StandardCharsets.UTF_8);
            return htm;
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String compressHtm(HtmlCompressor compressor, String content) {
        String result = compressor.compress(content);

        logger.info(String.format(
                "Compression time: %,d ms, Original size: %,d bytes, Compressed size: %,d bytes",
                compressor.getStatistics().getTime(),
                compressor.getStatistics().getOriginalMetrics().getFilesize(),
                compressor.getStatistics().getCompressedMetrics().getFilesize()
        ));

        return result;
    }
}
