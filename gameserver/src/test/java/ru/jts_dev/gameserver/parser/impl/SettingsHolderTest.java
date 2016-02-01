package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.parser.data.item.ItemDatasHolder;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.jts_dev.gameserver.constants.CharacterClass.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
@ContextConfiguration(classes = {SettingsHolder.class, ItemDatasHolder.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsHolderTest {
    private static final int STATS_COUNT = 11;
    @Autowired
    private SettingsHolder settingsHolder;
    @Autowired
    private ItemDatasHolder itemDatasHolder;

    /**
     * Assert that {@link SettingsHolder#recommendedStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public final void testParseRecommendedStats() throws Exception {
        assertThat(settingsHolder.getRecommendedStats(), is(not(empty())));
        assertThat(settingsHolder.getRecommendedStats(), iterableWithSize(STATS_COUNT));
    }


    /**
     * Assert that {@link SettingsHolder#maximumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public final void testParseMaximumStats() throws Exception {
        assertThat(settingsHolder.getMaximumStats(), is(not(empty())));
        assertThat(settingsHolder.getMaximumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * Assert that {@link SettingsHolder#minimumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public final void testParseMinimumStats() throws Exception {
        assertThat(settingsHolder.getMinimumStats(), is(not(empty())));
        assertThat(settingsHolder.getMinimumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * assertThat {@link SettingsHolder#recommendedStats} size
     * equals {@link SettingsHolder#minimumStats} and equals {@link SettingsHolder#maximumStats}
     *
     * @throws Exception
     */
    @Test
    public final void testThatStatsHasSameSize() throws Exception {
        assertThat(settingsHolder.getRecommendedStats().size(), allOf(
                is(equalTo(settingsHolder.getMinimumStats().size())),
                is(equalTo(settingsHolder.getMaximumStats().size()))
        ));
    }

    /**
     * Assert that {@link SettingsHolder#initialStartPoints} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public final void testParseInitialStartPoints() throws Exception {
        assertThat(settingsHolder.getInitialStartPoints().size(), greaterThan(0));

        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(HUMAN_FIGHTER), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(HUMAN_MAGICIAN), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(ELF_FIGHTER), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(ELF_MAGICIAN), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(DARKELF_FIGHTER), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(DARKELF_MAGICIAN), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(ORC_FIGHTER), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(ORC_SHAMAN), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(DWARF_APPRENTICE), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(KAMAEL_M_SOLDIER), is(not(empty()))));
        assertThat(settingsHolder.getInitialStartPoints(), hasEntry(equalTo(KAMAEL_F_SOLDIER), is(not(empty()))));
    }

    /**
     * Assert that {@link SettingsHolder#initialEquipments} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public final void testParseInitialEquipments() throws Exception {
        assertThat(settingsHolder.getInitialEquipments().size(), equalTo(STATS_COUNT));

        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(HUMAN_FIGHTER), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(HUMAN_MAGICIAN), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(ELF_FIGHTER), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(ELF_MAGICIAN), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(DARKELF_FIGHTER), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(DARKELF_MAGICIAN), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(ORC_FIGHTER), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(ORC_SHAMAN), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(DWARF_APPRENTICE), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(KAMAEL_M_SOLDIER), notNullValue()));
        assertThat(settingsHolder.getInitialEquipments(), hasEntry(equalTo(KAMAEL_F_SOLDIER), notNullValue()));
    }

    /**
     * Assert that all item definition in initial equipment {@link SettingsHolder#initialEquipments}
     * contains in itemdata.txt, loaded by {@link ItemDatasHolder}
     *
     * @throws Exception
     * @see ItemDatasHolder#getItemData()
     */
    @Test
    public final void testInitialEquipmentExists() throws Exception {
        for (Entry<CharacterClass, Map<String, Integer>> entry : settingsHolder.getInitialEquipments().entrySet()) {
            assertThat(entry.getValue().size(), greaterThan(0));
        }
    }

    /**
     * Assert that all item definition in initial equipment {@link SettingsHolder#initialEquipments}, has positive count
     *
     * @throws Exception
     */
    @Test
    public final void testInitialEquipmentHasPositiveCount() throws Exception {
        for (final Entry<CharacterClass, Map<String, Integer>> entry : settingsHolder.getInitialEquipments().entrySet()) {
            for (final String name : entry.getValue().keySet()) {
                assertThat("For class " + entry.getKey() + ", equipment " + name + " exist in itemdata",
                        itemDatasHolder.getItemData(), hasValue(hasProperty("name", equalTo(name))));
            }
        }
    }
}