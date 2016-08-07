package ru.jts_dev.gameserver.parser.html;

import com.google.common.collect.ImmutableMap;
import com.neovisionaries.i18n.LanguageCode;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public enum HtmlRepositoryType {
    ENABLE {
        @Override
        Map<LanguageCode, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) throws IOException {
            Map<LanguageCode, Path> result = new HashMap<>();
            try (DirectoryStream<Path> htmlDirStream = Files.newDirectoryStream(htmlDir)) {
                htmlDirStream.forEach(dir -> {
                    Path languageDirName = dir.getName(dir.getNameCount() - 1);
                    LanguageCode languageCode = LanguageCode.getByCode(languageDirName.toString());
                    if (languageCode != null) {
                        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                            stream.forEach(path2 ->
                                    result.put(languageCode, path2.getFileName()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            return ImmutableMap.copyOf(result);
        }
    },
    LAZY {
        @Override
        Map<LanguageCode, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) {
            return Collections.emptyMap();
        }
    };

    abstract Map<LanguageCode, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) throws IOException;
}
