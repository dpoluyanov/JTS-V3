package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.jts_dev.gameserver.parser.impl.PcParametersHolder.*;

/**
 * @author Camelion
 * @since 05.01.16
 */
@ContextConfiguration(classes = PcParametersHolder.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PcParametersHolderTest {
    @Autowired
    private PcParametersHolder pcParametersData;

    /**
     * Assert that {@link PcParametersHolder#collisionBoxes} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseCollisionBoxes() throws Exception {
        assertThat(pcParametersData.getCollisionBoxes().size()).isGreaterThan(0);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FFIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MFIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FMAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MMAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FELF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MELF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FELF_MAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MELF_MAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FDARKELF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MDARKELF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FDARKELF_MAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MDARKELF_MAGIC);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FORC_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MORC_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FSHAMAN);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MSHAMAN);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FDWARF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MDWARF_FIGHTER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(FKAMAEL_SOLDIER);
        assertThat(pcParametersData.getCollisionBoxes()).containsKey(MKAMAEL_SOLDIER);
    }
}
