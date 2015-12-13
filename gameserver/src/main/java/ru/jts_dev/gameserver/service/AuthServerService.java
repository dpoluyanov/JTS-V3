package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.messaging.GameServerInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Camelion
 * @since 09.12.15
 */
@Service
public class AuthServerService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${gameserver.id}")
    private byte serverId;

    @Value("${gameserver.host}")
    private String host;

    @Value("${gameserver.port}")
    private int port;

    /**
     * send game server status to auth server
     */
    @Scheduled(fixedDelay = 1000)
    public void register() throws UnknownHostException {
        jmsTemplate.convertAndSend("gameServersQueue", new GameServerInfo(serverId, InetAddress.getByName(host), port));
    }
}
