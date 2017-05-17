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

package ru.jts_dev.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

//@EntityScan(basePackages = {"ru.jts_dev.common", "ru.jts_dev.authserver"})
@SpringBootApplication(scanBasePackages = {"ru.jts_dev.common", "ru.jts_dev.authserver"})
public class AuthServerApplication implements CommandLineRunner {
    private final ConfigurableApplicationContext context;

    @Autowired
    public AuthServerApplication(ConfigurableApplicationContext context) {
        Assert.notNull(context, "Context must not be null!");

        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (context.containsBean("gameserverAppBuilder")) {
            context.getBean("gameserverAppBuilder", SpringApplicationBuilder.class)
                    .build()
                    .run(args);
        }
    }
}