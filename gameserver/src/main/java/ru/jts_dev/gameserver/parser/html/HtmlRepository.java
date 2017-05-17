/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.gameserver.parser.html;

import com.google.common.collect.Multimap;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.HtmlCompressorStatistics;
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
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Component
public class HtmlRepository {
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
    private void loadHtm() throws IOException {
        htmlDir = Paths.get(resourceLoader.getResource("html").getURI());
        Multimap<Locale, Path> htmlToLoad = config.getType().htmlToLoad(resourceLoader, htmlDir);
        htmlToLoad.entries().forEach(entry -> getHtml(entry.getKey(), entry.getValue().toString()));
    }

    @Cacheable(cacheNames = "html", key = "#language.toLanguageTag().concat('\').concat(#htmlName)")
    public String getHtml(Locale language, String htmlName) {
        String html = readHtml(language, htmlName);
        html = compressHtml(htmlCompressor, html);

        logger.debug("Loaded {} {}: {}", language, htmlName, html);
        return html;
    }

    private String readHtml(Locale language, String htmlName) {
        Path htmlPath = htmlDir.resolve(language.toLanguageTag()).resolve(htmlName);
        if (!Files.exists(htmlPath)) {
            throw new IllegalArgumentException("Can't find html: " + htmlPath);
        }
        try {
            byte[] content = Files.readAllBytes(htmlPath);
            return new String(content, UTF_8);
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
