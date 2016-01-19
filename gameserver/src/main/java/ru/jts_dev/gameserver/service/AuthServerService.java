package ru.jts_dev.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.messaging.GameServerInfo;
import ru.jts_dev.gameserver.config.GameServerConfig;

import javax.annotation.PostConstruct;
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

    @Autowired
    private GameServerConfig gameServerConfig;

    /**
     * send game server status to auth server
     */
    @PostConstruct
    public void register() throws UnknownHostException {
        byte serverId = gameServerConfig.getServerId();
        String host = gameServerConfig.getHost();
        int port = gameServerConfig.getPort();
        jmsTemplate.convertAndSend("gameServersQueue", new GameServerInfo(serverId, InetAddress.getByName(host), port));
    }
}
