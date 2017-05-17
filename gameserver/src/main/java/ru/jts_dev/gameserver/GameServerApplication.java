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

package ru.jts_dev.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Camelion
 * @since 09.12.15
 */
@EnableScheduling
@EntityScan(basePackages = {"ru.jts_dev.common", "ru.jts_dev.gameserver"})
@SpringBootApplication(scanBasePackages = {"ru.jts_dev.common", "ru.jts_dev.gameserver"})
public class GameServerApplication implements ApplicationContextAware {

    /**
     * Can be used AFTER bean initialization for all purposes,
     * where bean can't be inject via {@code @Autowired} annotation
     *
     * @see #setApplicationContext(ApplicationContext)
     */
    private static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class, args);
    }

    /**
     * Search bean of {@code clazz} type, and return initialized instance of it.
     * Can be used AFTER spring all spring bean initialization,
     * because {@code context} will be present after {@link SpringApplication#run(String...)} method.
     * Otherwise {@code NullPointerException} will be throwed.
     *
     * @param clazz - class type of searched bean
     * @param <T>   - returned type of bean
     * @return - initialized bean, if it present in Spring IoC container
     */
    public static <T> T getBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * Search bean of {@code clazz} type, and return initialized instance of it.
     * Can be used AFTER spring beans initialization,
     * because {@code context} will be present after {@link SpringApplication#run(String...)} method.
     * Otherwise {@code NullPointerException} will be throwed.
     *
     * @param beanName - name of searched bean
     * @param clazz    - class type of searched bean
     * @param <T>      - returned type of bean
     * @return - initialized bean, if it present in Spring IoC container
     */
    public static <T> T getBean(final String beanName, final Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
