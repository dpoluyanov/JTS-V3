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
