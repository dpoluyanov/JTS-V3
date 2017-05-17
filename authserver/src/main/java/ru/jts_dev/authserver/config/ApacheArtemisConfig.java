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

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants.HOST_PROP_NAME;
import static org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants.PORT_PROP_NAME;

/**
 * @author Camelion
 * @since 09.12.15
 */
@EnableJms
@Configuration
public class ApacheArtemisConfig {
    @Bean
    public ArtemisConfigurationCustomizer artemisCustomizer() {
        return configuration -> {
            Set<TransportConfiguration> acceptors = configuration.getAcceptorConfigurations();
            Map<String, Object> params = new HashMap<>();
            params.put(HOST_PROP_NAME, "localhost");
            params.put(PORT_PROP_NAME, "5445");
            TransportConfiguration tc = new TransportConfiguration(NettyAcceptorFactory.class.getName(), params);
            acceptors.add(tc);
        };
    }
}
