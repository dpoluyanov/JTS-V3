package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.constants.ItemTypes.ArmorType;
import ru.jts_dev.gameserver.constants.ItemTypes.EtcItemType;
import ru.jts_dev.gameserver.constants.ItemTypes.WeaponType;
import ru.jts_dev.gameserver.parser.data.item.ItemData;
import ru.jts_dev.gameserver.parser.data.item.SetData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

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

            assertThat(setData.getStrInc(), hasSize(2));
            assertThat(setData.getConInc(), hasSize(2));
            assertThat(setData.getDexInc(), hasSize(2));
            assertThat(setData.getIntInc(), hasSize(2));
            assertThat(setData.getMenInc(), hasSize(2));
            assertThat(setData.getWitInc(), hasSize(2));
        }
    }

    @Test
    public void testGetItemData() throws Exception {
        assertThat(itemDatasHolder.getItemData().size(), is(greaterThan(0)));
        for (ItemData itemData : itemDatasHolder.getItemData().values()) {
            assertThat(itemData.getName(), not(isEmptyOrNullString()));
            assertThat("For item " + itemData.getName(), itemData.getItemId(), greaterThan(0));
            assertThat("For item " + itemData.getName(), itemData.getItemClass(), is(notNullValue()));
            assertThat("For item " + itemData.getName(), itemData.getItemType(), is(notNullValue()));
            assertThat("For item " + itemData.getName(), itemData.getDelayShareGroup(), greaterThanOrEqualTo(-1));

            testItemMultiSkillList(itemData); // TODO: 10.01.16 test exists skill name in skilldata.txt
            assertThat(itemData.getRecipeId(), greaterThanOrEqualTo(0)); // TODO: 11.01.16 test exists in recipe data file
            assertThat("For item " + itemData.getName(), itemData.getWeight(), greaterThan(0));

            testShield(itemData);
            testArmorType(itemData);
            testWeaponType(itemData);
            testEtcItemType(itemData);
        }
    }

    private void testItemMultiSkillList(ItemData itemData) {
        assertThat("For item " + itemData.getName(), itemData.getItemMultiSkillList(), is(notNullValue()));
        assertThat("For item " + itemData.getName(), itemData.getItemMultiSkillList(), everyItem(not(isEmptyOrNullString())));
    }

    private void testShield(ItemData itemData) {
        if (itemData.getShieldDefenseRate() > 0 || itemData.getShieldDefense() > 0) {
            assertTrue("For item " + itemData.getName(), itemData.getWeaponType() == WeaponType.NONE
                    && itemData.getEtcItemType() == EtcItemType.NONE);
            assertThat("For item " + itemData.getName(), itemData.getShieldDefense(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getShieldDefenseRate(), greaterThan(0));
        }
    }

    private void testArmorType(ItemData itemData) {
        if (itemData.getArmorType() != ArmorType.NONE) {
            assertThat("For item " + itemData.getName(), itemData.getWeaponType(), is(WeaponType.NONE));
            assertThat("For item " + itemData.getName(), itemData.getEtcItemType(), is(EtcItemType.NONE));
        }
    }

    private void testWeaponType(ItemData itemData) {
        if (itemData.getWeaponType() != WeaponType.NONE) {
            assertThat("For item " + itemData.getName(), itemData.getArmorType(), is(ArmorType.NONE));
            assertThat("For item " + itemData.getName(), itemData.getEtcItemType(), is(EtcItemType.NONE));
        }
    }

    private void testEtcItemType(ItemData itemData) {
        if (itemData.getEtcItemType() != EtcItemType.NONE) {
            assertThat("For item " + itemData.getName(), itemData.getArmorType(), is(ArmorType.NONE));
            assertThat("For item " + itemData.getName(), itemData.getWeaponType(), is(WeaponType.NONE));
        }
    }
}