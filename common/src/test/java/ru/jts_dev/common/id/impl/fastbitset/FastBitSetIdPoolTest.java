package ru.jts_dev.common.id.impl.fastbitset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.common.config.UtilsConfig;
import ru.jts_dev.common.id.IdPool;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Java-man
 * @since 30.07.16
 */
@SpringJUnitConfig({FastBitSetIdPool.class, UtilsConfig.class})
@TestPropertySource(properties = {"jts.common.bitset.max-size=20000"})
public class FastBitSetIdPoolTest {
    @Autowired
    private IdPool idPool;

    @DirtiesContext
    @Test
    public void testIdBorrow() {
        IntStream.rangeClosed(1, 10_000).forEach(value -> {
            final int id = idPool.borrow();
            assertThat(id).isEqualTo(value);
        });

        IntStream.rangeClosed(1, 10_000).parallel().forEach(value -> {
            final int id = idPool.borrow();
            assertThat(id).isGreaterThanOrEqualTo(10_000);
        });
    }

    @DirtiesContext
    @Test
    public void testIdRelease() {
        IntStream.rangeClosed(1, 20_000).forEach(value -> idPool.borrow());

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> idPool.release(value));
    }

    @DirtiesContext
    @Test
    public void testIdReusing() {
        IntStream.rangeClosed(1, 20_000).forEach(value -> idPool.borrow());

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> idPool.release(value));

        IntStream.rangeClosed(1, 20_000).parallel().forEach(value -> {
            int id = idPool.borrow();
            assertThat(id).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(20_000);
        });
    }

    @DirtiesContext
    @Test
    public void releaseZeroIdThrowsException() {
        Throwable exception = expectThrows(IndexOutOfBoundsException.class, () -> idPool.release(0));
        assertThat(exception.getMessage()).isEqualTo("index must be > 0 current: 0");
    }

    @DirtiesContext
    @Test
    public void releaseNegativeIdThrowsException() {
        Throwable exception = expectThrows(IndexOutOfBoundsException.class, () -> idPool.release(-1));
        assertThat(exception.getMessage()).isEqualTo("index must be > 0 current: -1");
    }
}