package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.jts_dev.gameserver.constants.CharacterClass.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
@ContextConfiguration(classes = SettingsHolder.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsDataTest {
    private static final int STATS_COUNT = 11;
    @Autowired
    private SettingsHolder settingsData;

    /**
     * Assert that {@link SettingsHolder#recommendedStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseRecommendedStats() throws Exception {
        assertThat(settingsData.getRecommendedStats(), is(not(empty())));
        assertThat(settingsData.getRecommendedStats(), iterableWithSize(STATS_COUNT));
    }


    /**
     * Assert that {@link SettingsHolder#maximumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseMaximumStats() throws Exception {
        assertThat(settingsData.getMaximumStats(), is(not(empty())));
        assertThat(settingsData.getMaximumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * Assert that {@link SettingsHolder#minimumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseMinimumStats() throws Exception {
        assertThat(settingsData.getMinimumStats(), is(not(empty())));
        assertThat(settingsData.getMinimumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * assertThat {@link SettingsHolder#recommendedStats} size
     * equals {@link SettingsHolder#minimumStats} and equals {@link SettingsHolder#maximumStats}
     *
     * @throws Exception
     */
    @Test
    public void testThatStatsHasSameSize() throws Exception {
        assertThat(settingsData.getRecommendedStats().size(), allOf(
                is(equalTo(settingsData.getMinimumStats().size())),
                is(equalTo(settingsData.getMaximumStats().size()))
        ));
    }

    /**
     * Assert that {@link SettingsHolder#initialStartPoints} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseInitialStartPoints() throws Exception {
        assertThat(settingsData.getInitialStartPoints().size(), greaterThan(0));

        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(HUMAN_FIGHTER), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(HUMAN_MAGICIAN), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(ELF_FIGHTER), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(ELF_MAGICIAN), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(DARKELF_FIGHTER), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(DARKELF_MAGICIAN), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(ORC_FIGHTER), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(ORC_SHAMAN), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(DWARF_APPRENTICE), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(KAMAEL_M_SOLDIER), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo(KAMAEL_F_SOLDIER), is(not(empty()))));
    }
}