package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.messaging.GameServerInfo;

/**
 * @author Camelion
 * @since 09.12.15
 */
@EnableJms
@EnableScheduling
@Service
public class AuthServerService {
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * send game server status to auth server
     */
    @Scheduled(fixedDelay = 1000)
    public void register() {
        jmsTemplate.convertAndSend("gameServersQueue", new GameServerInfo(1, null, 2108));
    }
}
