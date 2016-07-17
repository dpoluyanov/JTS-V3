package ru.jts_dev.common.id.impl.bitset;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.common.config.UtilsConfig;
import ru.jts_dev.common.id.IdPool;
import ru.jts_dev.common.id.impl.AllocationException;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Camelion
 * @since 16.07.16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BitSetIdPool.class, UtilsConfig.class})
@TestPropertySource(properties = {"jts.common.bitset.max-size=20000"})
public class BitSetIdPoolTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    private IdPool idPool;

    @DirtiesContext
    @Test(timeout = 500L)
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

    @DirtiesContext
    @Test
    public void testIdRelease() throws Exception {
        IntStream.rangeClosed(1, 20_000).forEach(value -> idPool.borrow());

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> idPool.release(value));
    }

    @DirtiesContext
    @Test
    public void testIdReusing() throws Exception {
        IntStream.rangeClosed(1, 20_000).forEach(value -> idPool.borrow());

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> idPool.release(value));

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> {
            int id = idPool.borrow();
            assertThat(id, is(allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(20_000))));
        });
    }

    @DirtiesContext
    @Test
    public void releaseZeroIdThrowsException() throws Exception {
        expectedEx.expect(IndexOutOfBoundsException.class);
        expectedEx.expectMessage(is(startsWith("index must be > 0 and <= 20000 current: 0")));

        idPool.release(0);
    }

    @DirtiesContext
    @Test
    public void releaseNegativeIdThrowsException() throws Exception {
        expectedEx.expect(IndexOutOfBoundsException.class);
        expectedEx.expectMessage(is(startsWith("index must be > 0 and <= 20000 current: -1")));

        idPool.release(-1);
    }

    @DirtiesContext
    @Test
    public void throwsExceptionIfNoFreeIndexes() throws Exception {
        expectedEx.expect(AllocationException.class);
        expectedEx.expectMessage(is(equalTo("No available indexes in pool")));

        IntStream.rangeClosed(0, 20000).parallel().forEach(value -> {
            idPool.borrow();
        });
    }
}