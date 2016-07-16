package ru.jts_dev.common.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Camelion
 * @since 15.07.16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UtilsConfig.class)
public class UtilsConfigTest {
    @Autowired
    private Random random;

    @Test
    public void randomBeanMustBeDefined() {
        assertThat(random, is(notNullValue()));
    }
}