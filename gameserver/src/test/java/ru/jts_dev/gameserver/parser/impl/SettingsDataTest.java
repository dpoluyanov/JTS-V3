package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
@ContextConfiguration(classes = SettingsData.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsDataTest {
    private static final int STATS_COUNT = 11;
    @Autowired
    private SettingsData settingsData;

    /**
     * Assert that {@link SettingsData#recommendedStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseRecommendedStats() throws Exception {
        assertThat(settingsData.getRecommendedStats(), is(not(empty())));
        assertThat(settingsData.getRecommendedStats(), iterableWithSize(STATS_COUNT));
    }


    /**
     * Assert that {@link SettingsData#maximumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseMaximumStats() throws Exception {
        assertThat(settingsData.getMaximumStats(), is(not(empty())));
        assertThat(settingsData.getMaximumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * Assert that {@link SettingsData#minimumStats} parsed correctly
     *
     * @throws Exception
     */
    @Test
    public void testParseMinimumStats() throws Exception {
        assertThat(settingsData.getMinimumStats(), is(not(empty())));
        assertThat(settingsData.getMinimumStats(), iterableWithSize(STATS_COUNT));
    }

    /**
     * assertThat {@link SettingsData#recommendedStats} size
     * equals {@link SettingsData#minimumStats} and equals {@link SettingsData#maximumStats}
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