package ru.jts_dev.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@EntityScan(basePackages = {"ru.jts_dev.common", "ru.jts_dev.authserver"})
@SpringBootApplication(scanBasePackages = {"ru.jts_dev.common", "ru.jts_dev.authserver"})
public class AuthServerApplication implements CommandLineRunner {
    @Autowired
    private ConfigurableApplicationContext context;

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