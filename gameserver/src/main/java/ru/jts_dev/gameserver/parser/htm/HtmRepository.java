package ru.jts_dev.gameserver.parser.htm;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.Language;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.READ;

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
    public HtmRepository(HtmRepositoryConfig config, HtmlCompressor htmlCompressor) {
        this.config = config;
        this.htmlCompressor = htmlCompressor;
    }

    public static String inputStreamToString(InputStream inputStream, int size, Charset charset) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        char[] buffer = new char[size];
        int length;
        while ((length = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, length);
        }
        return builder.toString();
    }

    @PostConstruct
    private void loadHtm() {
        //if (config.getHtmRepositoryType() != HtmRepositoryType.ENABLE)
        //   return;

        for (Language language : Language.values()) {
            try {
                Path htmDir = Paths.get(context.getResource("htm").getURI());

                Path archivePath = htmDir.resolve(language.getShortName() + ".zip");
                if (Files.exists(archivePath)) {
                    logger.info("Found htm archive {}", archivePath.getFileName());
                    openArchive(language, archivePath);
                }

                Path htmPath = htmDir.resolve(language.getShortName());
                if (!Files.exists(archivePath)) {
                    logger.error("Can't find directory {}", htmPath);
                    continue;
                }

                try (Stream<Path> stream = Files.walk(htmPath)) {
                    stream.filter(path -> !Files.isDirectory(path))
                            .forEach(path -> addHtm(language, path.getFileName().toString(), null));
                }
            } catch (IOException | ArchiveException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Cacheable(cacheNames = "htm", key = "#language.toString() + \"_\" + #htmPath")
    public String getHtm(Language language, String htmPath) {
        if (config.getHtmRepositoryType() == HtmRepositoryType.DISABLE)
            return readHtm(language, htmPath);
        else
            return getCachedHtm(language, htmPath);
    }

    private String getCachedHtm(Language language, String htmPath) {
        String htm = readHtm(language, htmPath);
        htm = compressHtm(htmlCompressor, htm);
        logger.debug("Loaded {} {}: {}", language, htmPath, htm);
        return htm;
    }

    private String addHtm(Language language, String htmName, String content) {
        if (content == null) {
            content = readHtm(language, htmName);
        }
        content = compressHtm(htmlCompressor, content);
        logger.debug("Loaded {} {}: {}", language, htmName, content);
        return content;
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

        logger.debug(String.format("Compression time: %,d ms, Original size: %,d bytes, Compressed size: %,d bytes",
                compressor.getStatistics().getTime(), compressor.getStatistics().getOriginalMetrics().getFilesize(),
                compressor.getStatistics().getCompressedMetrics().getFilesize()));

        return result;
    }

    private void openArchive(Language language, Path path) throws IOException, ArchiveException {
        try (InputStream originalInput = Files.newInputStream(path, READ);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(originalInput);
             ArchiveInputStream input = new ArchiveStreamFactory().createArchiveInputStream(bufferedInputStream)) {
            ArchiveEntry entry;
            while ((entry = input.getNextEntry()) != null) {
                if (entry.isDirectory() || !entry.getName().endsWith(".htm")) {
                    continue;
                }

                String htm = inputStreamToString(input, (int) entry.getSize(), UTF_8);
                addHtm(language, entry.getName(), htm);
            }
        }
    }
}
