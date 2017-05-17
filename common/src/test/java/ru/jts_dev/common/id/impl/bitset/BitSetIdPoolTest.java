/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.common.id.impl.bitset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.common.config.UtilsConfig;
import ru.jts_dev.common.id.IdPool;
import ru.jts_dev.common.id.impl.AllocationException;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Camelion
 * @since 16.07.16
 */
@SpringJUnitConfig({BitSetIdPool.class, UtilsConfig.class})
@TestPropertySource(properties = {"jts.common.bitset.max-size=20000"})
public class BitSetIdPoolTest {
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
        assertThat(exception.getMessage()).isEqualTo("index must be > 0 and <= 20000 current: 0");
    }

    @DirtiesContext
    @Test
    public void releaseNegativeIdThrowsException() {
        Throwable exception = expectThrows(IndexOutOfBoundsException.class, () -> idPool.release(-1));
        assertThat(exception.getMessage()).isEqualTo("index must be > 0 and <= 20000 current: -1");
    }

    @DirtiesContext
    @Test
    public void throwsExceptionIfNoFreeIndexes() {
        Throwable exception = expectThrows(AllocationException.class, () ->
                IntStream.rangeClosed(0, 20000).parallel().forEach(value -> idPool.borrow()));
        assertThat(exception.getMessage()).isEqualTo("No available indexes in pool");
    }
}