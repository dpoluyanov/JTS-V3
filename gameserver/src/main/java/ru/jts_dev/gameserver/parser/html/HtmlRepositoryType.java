package ru.jts_dev.gameserver.parser.html;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public enum HtmlRepositoryType {
    ENABLE {
        @Override
        Multimap<Locale, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) throws IOException {
            Multimap<Locale, Path> result = HashMultimap.create();
            try (DirectoryStream<Path> htmlDirStream = Files.newDirectoryStream(htmlDir)) {
                htmlDirStream.forEach(dir -> {
                    Path languageDirName = dir.getName(dir.getNameCount() - 1);
                    Locale locale = Locale.forLanguageTag(languageDirName.toString());
                    if (locale != null) {
                        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                            stream.forEach(path2 ->
                                    result.put(locale, path2.getFileName()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            return ImmutableMultimap.copyOf(result);
        }
    },
    LAZY {
        @Override
        Multimap<Locale, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) {
            return ImmutableMultimap.of();
        }
    };

    abstract Multimap<Locale, Path> htmlToLoad(ResourceLoader resourceLoader, Path htmlDir) throws IOException;
}
