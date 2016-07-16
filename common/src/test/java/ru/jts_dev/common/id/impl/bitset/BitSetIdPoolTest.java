package ru.jts_dev.common.id.impl.bitset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.common.id.IdPool;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author Camelion
 * @since 16.07.16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BitSetIdPool.class})
public class BitSetIdPoolTest {
    @Autowired
    private IdPool idPool;

    @DirtiesContext
    @Test(timeout = 1000L)
    public void testIdBorrow() throws Exception {
        IntStream.rangeClosed(1, 10_000).forEach(value -> {
            final int id = idPool.borrow();
            assertThat(id, equalTo(value));
        });

        IntStream.rangeClosed(1, 10_000).parallel().forEach(value -> {
            final int id = idPool.borrow();
            assertThat(id, greaterThanOrEqualTo(10_000));
        });
    }

    @Test
    public void testIdRelease() throws Exception {
        IntStream.rangeClosed(1, 20_000).forEach(value -> idPool.borrow());

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> idPool.release(value));
    }
}