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

package ru.jts_dev.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 02.12.15
 */
@Configuration
public class UtilsConfig {

    /**
     * Random bean should be loaded lazily, and only in points, that it's really needed,
     * because random is instance of {@link ThreadLocalRandom}
     * and if all random beans will be initialized in single thread - no performance impact will be provided.
     * <p>
     * <pre class="code">
     * &#064;Autowired ApplicationContent context;
     * ...
     * Random random = context.getBean(Random.class);
     * ...
     * </pre>
     *
     * @return ThreadLocalRandom for caller thread
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Random random() {
        return ThreadLocalRandom.current();
    }
}
