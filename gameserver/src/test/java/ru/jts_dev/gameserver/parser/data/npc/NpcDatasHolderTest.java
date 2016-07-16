package ru.jts_dev.gameserver.parser.data.npc;

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
 * @since 16.02.16
 */
@ContextConfiguration(classes = NpcDatasHolder.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class NpcDatasHolderTest {

    @Autowired
    private NpcDatasHolder npcDatasHolder;

    @Test
    public void testGetNpcData() throws Exception {
        assertThat(npcDatasHolder.getNpcData().size(), is(greaterThan(0)));
    }
}