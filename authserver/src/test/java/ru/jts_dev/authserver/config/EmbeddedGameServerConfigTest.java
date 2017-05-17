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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Camelion
 * @since 10.12.15
 */
@SpringJUnitConfig(EmbeddedGameServerConfig.class)
@TestPropertySource(properties = "authserver.gameserver.embedded = false")
public class EmbeddedGameServerConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DirtiesContext
    public void test() {
        assertThat(context.containsBean("embeddedGameServerConfig"));
    }

    @TestPropertySource(properties = "authserver.gameserver.embedded = true")
    @SpringJUnitConfig(EmbeddedGameServerConfig.class)
    public static class EmbeddedGameServerConfigMatched {

        @Autowired
        private ApplicationContext context;

        @Test
        @DirtiesContext
        public void test() {
            assertThat(context.containsBean("embeddedGameServerConfig"));
        }
    }
}