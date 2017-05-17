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
