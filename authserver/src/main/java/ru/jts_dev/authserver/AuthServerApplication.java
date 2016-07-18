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