package ru.jts_dev.gameserver.parser.data.npc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Camelion
 * @since 16.02.16
 */
@SpringJUnitConfig(NpcDatasHolder.class)
public class NpcDatasHolderTest {
    @Autowired
    private NpcDatasHolder npcDatasHolder;

    @Test
    public void testGetNpcData() throws Exception {
        assertThat(npcDatasHolder.getNpcData().size()).isGreaterThan(0);
    }
}