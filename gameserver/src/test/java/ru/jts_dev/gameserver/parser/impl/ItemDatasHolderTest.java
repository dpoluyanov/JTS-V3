package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * @author Camelion
 * @since 07.01.16
 */
@ContextConfiguration(classes = ItemDatasHolder.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemDatasHolderTest {

    @Autowired
    private ItemDatasHolder itemDatasHolder;

    @Test
    public void testGetSetsData() throws Exception {
        assertThat(itemDatasHolder.getSetsData().size(), is(greaterThan(0)));
    }
}