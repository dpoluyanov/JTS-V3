package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.parser.data.item.SetData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        for (SetData setData : itemDatasHolder.getSetsData().values()) {
            assertThat(setData.getSlotChest(), greaterThan(0));

            assertThat(setData.getSlotAdditional(), is(not(isEmptyOrNullString())));
            assertThat(setData.getSetSkill(), is(not(isEmptyOrNullString())));
            assertThat(setData.getSetEffectSkill(), is(not(isEmptyOrNullString())));
            assertThat(setData.getSetAdditionalEffectSkill(), is(not(isEmptyOrNullString())));

            assertThat(setData.getStrInc().length, equalTo(2));
            assertThat(setData.getConInc().length, equalTo(2));
            assertThat(setData.getDexInc().length, equalTo(2));
            assertThat(setData.getIntInc().length, equalTo(2));
            assertThat(setData.getMenInc().length, equalTo(2));
            assertThat(setData.getWitInc().length, equalTo(2));
        }
    }
}