package ru.jts_dev.gameserver.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("human_fighter"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("human_magician"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("elf_fighter"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("elf_magician"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("darkelf_fighter"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("darkelf_magician"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("orc_fighter"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("orc_shaman"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("dwarf_apprentice"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("kamael_m_soldier"), is(not(empty()))));
        assertThat(settingsData.getInitialStartPoints(), hasEntry(equalTo("kamael_f_soldier"), is(not(empty()))));
    }
}