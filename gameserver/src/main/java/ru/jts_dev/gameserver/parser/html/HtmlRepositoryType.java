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
