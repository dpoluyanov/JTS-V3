package ru.jts_dev.common.id;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Java-man
 * @since 24.01.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BitSetIdPool.class})
public class IdPoolTest {
    @Autowired
    private IdPool idPool;

    @Test
    public void testIdBorrow() throws Exception {
        int id = idPool.borrow();
        assertThat(id, is(0));
    }

    @Test
    public void testIdRelease() throws Exception {
        idPool.release(0);
    }
}
