package ru.jts_dev.gameserver.parser.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.constants.CrystalType;
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
            assertThat(itemData.getRecipeId(), greaterThanOrEqualTo(0)); // TODO: 11.01.16 test exists in recipe.txt
            assertThat("For item " + itemData.getName(), itemData.getBlessed(), anyOf(is(0), is(2)));
            assertThat("For item " + itemData.getName(), itemData.getWeight(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getDefaultAction(), is(notNullValue()));
            assertThat("For item " + itemData.getName(), itemData.getConsumeType(), is(notNullValue()));
            assertThat("For item " + itemData.getName(), itemData.getInitialCount(), greaterThanOrEqualTo(1));

            assertThat("For item " + itemData.getName(), itemData.getSoulshotCount(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getSpiritshotCount(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getReducedSoulshot(), anyOf(is(empty()), hasSize(2)));
            assertThat("For item " + itemData.getName(), itemData.getReducedSpiritshot(), is(empty()));
            assertThat("For item " + itemData.getName(), itemData.getReducedMpConsume(), anyOf(is(empty()), hasSize(2)));

            assertThat("For item " + itemData.getName(), itemData.getImmediateEffect(), anyOf(is(0), is(1)));
            assertThat("For item " + itemData.getName(), itemData.getExImmediateEffect(), anyOf(is(0), is(1), is(2)));

            assertThat("For item " + itemData.getName(), itemData.getDropPeriod(), greaterThan(0));
            assertThat("For item " + itemData.getName(), itemData.getDuration(),
                    allOf(greaterThanOrEqualTo(-1), is(not(0))));
            assertThat("For item " + itemData.getName(), itemData.getUseSkillDistime(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getPeriod(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName(), itemData.getEquipReuseDelay(), greaterThanOrEqualTo(0));

            assertThat("For item " + itemData.getName(), itemData.getItemSkill(), not(isEmptyOrNullString()));
            assertThat("For item " + itemData.getName(), itemData.getCriticalAttackSkill(), not(isEmptyOrNullString()));
            assertThat("For item " + itemData.getName(), itemData.getAttackSkill(), is("none"));
            assertThat("For item " + itemData.getName(), itemData.getItemSkillEnchantedFour(), not(isEmptyOrNullString()));

            assertThat("For item " + itemData.getName(), itemData.getMaterialType(), is(notNullValue()));

            testCrystal(itemData);
            testCapsuledItems(itemData);
            testMagicSkill(itemData);
            testPrice(itemData);

            testShield(itemData);
            testArmorType(itemData);
            testWeaponType(itemData);
            testEtcItemType(itemData);
        }
    }

    private void testCrystal(ItemData itemData) {
        assertThat("For item " + itemData.getName(), itemData.getCrystalType(), is(notNullValue()));
        assertThat("For item " + itemData.getName(), itemData.getCrystalCount(), greaterThanOrEqualTo(0));

        if(itemData.getCrystalType() == CrystalType.CRYSTAL_FREE) {
            assertThat("For item " + itemData.getName(), itemData.getCrystalCount(), is(0));
        }
    }

    private void testCapsuledItems(ItemData itemData) {
        assertThat("For item " + itemData.getName(), itemData.getCapsuledItems(), is(notNullValue()));

        // TODO: 13.01.16 тест наличия предмета в списке, тест шансов, минимального кол-ва
        for (ItemData.CapsuledItemData capsuledItemData : itemData.getCapsuledItems()) {
            assertThat("For item " + itemData.getName(), capsuledItemData.getItemName(), not(isEmptyOrNullString()));

            assertThat("For item " + itemData.getName() + ", Capsuled item " + itemData.getName(),
                    itemDatasHolder.getItemData(), hasValue(hasProperty("name", equalTo(capsuledItemData.getItemName()))));

            assertThat("For item " + itemData.getName() + ", Capsuled item " + itemData.getName(),
                    capsuledItemData.getMaxCount(), greaterThanOrEqualTo(0));
            assertThat("For item " + itemData.getName() + ", Capsuled item " + itemData.getName(),
                    capsuledItemData.getMinCount(), allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(capsuledItemData.getMaxCount())));

            assertThat("For item " + itemData.getName() + ", Capsuled item " + itemData.getName(),
                    capsuledItemData.getChance(), allOf(greaterThan(0.0), lessThanOrEqualTo(100.0)));
        }
    }

    private void testMagicSkill(ItemData itemData) {
        assertThat("For item " + itemData.getName(), itemData.getMagicSkill(), not(isEmptyOrNullString()));

        if (itemData.getMagicSkill().equals("none")) {
            assertThat("For item " + itemData.getName(), itemData.getMagicSkillUnknownValue(), is(0));
        } else {
            assertThat("For item " + itemData.getName(), itemData.getMagicSkillUnknownValue(), greaterThan(0));
        }
    }

    private void testPrice(ItemData itemData) {
        assertThat("For item " + itemData.getName(), itemData.getPrice(), greaterThanOrEqualTo(0L));

        if (itemData.getPrice() > 0)
            assertThat("For item " + itemData.getName(), itemData.getPrice(), is(equalTo(itemData.getDefaultPrice())));
        assertThat("For item " + itemData.getName(), itemData.getDefaultPrice(), greaterThanOrEqualTo(0L));
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