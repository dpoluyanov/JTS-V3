package ru.jts_dev.gameserver.parser.htm;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressorStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.Language;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

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
    private ApplicationContext context;
    @Autowired
    private CacheManager cacheManager;

    private Cache[] caches;

    @Autowired
    public HtmRepository(HtmRepositoryConfig config, HtmlCompressor htmlCompressor) {
        this.config = config;
        this.htmlCompressor = htmlCompressor;
    }

    @PostConstruct
    private void loadHtm() {
        caches = new Cache[Language.values().length];
        for (Language language : Language.values()) {
            caches[language.ordinal()] = cacheManager.getCache("htm-" + language.getShortName());
        }

        if (config.getHtmRepositoryType() != HtmRepositoryType.ENABLE)
            return;

        for (Language language : Language.values()) {
            try {
                Path htmDir = Paths.get(context.getResource("htm").getURI());

                Path htmPath = htmDir.resolve(language.getShortName());
                if (!Files.exists(htmPath)) {
                    logger.error("Can't find directory {}", htmPath);
                    continue;
                }

                try (Stream<Path> stream = Files.walk(htmPath)) {
                    stream.filter(path -> !Files.isDirectory(path))
                            .forEach(path -> addHtm(language, path));
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public String getHtm(Language language, String htmPath) {
        if (config.getHtmRepositoryType() == HtmRepositoryType.DISABLE)
            return readHtm(language, htmPath);
        else
            return getCachedHtm(language, htmPath);
    }

    private String getCachedHtm(Language language, String htmPath) {
        Cache cache = caches[language.ordinal()];

        String htm = cache.get(htmPath, String.class);
        if (htm != null && !htm.isEmpty())
            return htm;

        htm = readHtm(language, htmPath);
        htm = compressHtm(htmlCompressor, htm);

        logger.debug("Loaded {} {}: {}", language, htmPath, htm);
        return htm;
    }

    private String addHtm(Language language, Path htmPath) {
        String htm = readHtm(language, htmPath.toString());
        htm = compressHtm(htmlCompressor, htm);

        Cache cache = caches[language.ordinal()];
        cache.putIfAbsent(htmPath.toString(), htm);

        logger.debug("Loaded {} {}: {}", language, htmPath, htm);
        return htm;
    }

    private String readHtm(Language language, String htmName) {
        try {
            Path htmPath = Paths.get(htmName);
            if (!Files.exists(htmPath)) {
                Path htmDir = Paths.get(context.getResource("htm/" + language.getShortName()).getURI());
                htmPath = htmDir.resolve(htmName);
            }

            byte[] content = Files.readAllBytes(htmPath);
            String htm = new String(content, UTF_8);
            return htm;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String compressHtm(HtmlCompressor compressor, String content) {
        String result = compressor.compress(content);

        HtmlCompressorStatistics statistics = compressor.getStatistics();
        logger.debug(String.format("Compression time: %,d ms, Original size: %,d bytes, Compressed size: %,d bytes",
                statistics.getTime(), statistics.getOriginalMetrics().getFilesize(),
                statistics.getCompressedMetrics().getFilesize()));

        return result;
    }
}
