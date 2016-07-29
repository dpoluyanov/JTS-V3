package ru.jts_dev.common.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Camelion
 * @since 15.07.16
 */
@SpringJUnitConfig(UtilsConfig.class)
public class UtilsConfigTest {
    @Autowired
    private Random random;

    @Test
    public void randomBeanMustBeDefined() {
        assertThat(random).isNotNull();
    }
}