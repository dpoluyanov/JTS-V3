package ru.jts_dev.gameserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Camelion
 * @since 09.12.15
 */
@EnableJms
@SpringBootApplication
public class GameServerApplication implements ApplicationRunner{

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jmsTemplate.convertAndSend("gameserversQueue", "Hello!");
    }
}
