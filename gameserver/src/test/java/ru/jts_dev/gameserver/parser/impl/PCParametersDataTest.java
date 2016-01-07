package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.jts_dev.gameserver.parser.impl.PCParametersHolder.*;

/**
 * @author Camelion
 * @since 05.01.16
 */
@ContextConfiguration(classes = PCParametersHolder.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PCParametersDataTest {
    @Autowired
    private PCParametersHolder pcParametersData;

    /**
     * Assert that {@link PCParametersHolder#collisionBoxes} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseCollisionBoxes() throws Exception {
        assertThat(pcParametersData.getCollisionBoxes().size(), greaterThan(0));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FFIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MFIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FMAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MMAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FELF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MELF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FELF_MAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MELF_MAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FDARKELF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MDARKELF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FDARKELF_MAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MDARKELF_MAGIC)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FORC_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MORC_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FSHAMAN)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MSHAMAN)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FDWARF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MDWARF_FIGHTER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(FKAMAEL_SOLDIER)));
        assertThat(pcParametersData.getCollisionBoxes(), hasKey(equalTo(MKAMAEL_SOLDIER)));
    }
}
