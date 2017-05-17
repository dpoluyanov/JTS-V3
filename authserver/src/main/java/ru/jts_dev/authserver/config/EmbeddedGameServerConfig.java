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

package ru.jts_dev.authserver.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Camelion
 * @since 10.12.15
 */
@ConditionalOnProperty("authserver.gameserver.embedded")
@Configuration
public class EmbeddedGameServerConfig {
    @Bean
    public SpringApplicationBuilder gameserverAppBuilder() throws ClassNotFoundException {
        return new SpringApplicationBuilder(Class.forName("ru.jts_dev.gameserver.GameServerApplication"))
                .bannerMode(Banner.Mode.OFF)
                .main(Class.forName("ru.jts_dev.gameserver.GameServerApplication"))
                .profiles("embedded-gs");
    }
}
