package ru.jts_dev.gameserver.parser.impl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.gameserver.parser.data.item.ItemDatasHolder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static ru.jts_dev.gameserver.constants.CharacterClass.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
@SpringJUnitConfig(classes = {SettingsHolder.class, ItemDatasHolder.class})
public class SettingsHolderTest {
    private static final int STATS_COUNT = 11;
    @Autowired
    private SettingsHolder settingsHolder;
    @Autowired
    private ItemDatasHolder itemDatasHolder;

    /**
     * Assume that {@link SettingsHolder#recommendedStats} parsed correctly
     */
    @Test
    @DisplayName("recommended stats has " + STATS_COUNT + " items")
    public final void testParseRecommendedStats() {
        assumeTrue(settingsHolder.getRecommendedStats().size() == STATS_COUNT);
    }


    /**
     * Assume that {@link SettingsHolder#maximumStats} parsed correctly
     */
    @Test
    @DisplayName("maximum stats has " + STATS_COUNT + " items")
    public final void testParseMaximumStats() {
        assumeTrue(settingsHolder.getMaximumStats().size() == STATS_COUNT);
    }

    /**
     * Assume that {@link SettingsHolder#minimumStats} parsed correctly
     */
    @Test
    @DisplayName("minimum stats has " + STATS_COUNT + " items")
    public final void testParseMinimumStats() {
        assumeTrue(settingsHolder.getMinimumStats().size() == STATS_COUNT);
    }

    /**
     * Assume that {@link SettingsHolder#recommendedStats} size
     * equals {@link SettingsHolder#minimumStats} and equals {@link SettingsHolder#maximumStats}
     */
    @Test
    @DisplayName("minimum stats has " + STATS_COUNT + " items")
    public final void testThatStatsHasSameSize() throws Exception {
        assumeTrue(settingsHolder.getRecommendedStats().size() == settingsHolder.getMinimumStats().size());
        assumeTrue(settingsHolder.getRecommendedStats().size() == settingsHolder.getMaximumStats().size());
    }

    /**
     * Assume that {@link SettingsHolder#initialStartPoints} parsed correctly
     */
    @TestFactory
    public final Stream<DynamicTest> testParseInitialStartPoints() {
        return Stream.of(
                dynamicTest("at least one initial start point has been loaded",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().isEmpty())),

                dynamicTest("initial start points contains " + HUMAN_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(HUMAN_FIGHTER))),
                dynamicTest("initial start points for " + HUMAN_FIGHTER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(HUMAN_FIGHTER).isEmpty())),

                dynamicTest("initial start points contains " + HUMAN_MAGICIAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(HUMAN_MAGICIAN))),
                dynamicTest("initial start points for " + HUMAN_MAGICIAN + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(HUMAN_MAGICIAN).isEmpty())),

                dynamicTest("initial start points contains " + ELF_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(ELF_FIGHTER))),
                dynamicTest("initial start points for " + ELF_FIGHTER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ELF_FIGHTER).isEmpty())),

                dynamicTest("initial start points contains " + DARKELF_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(DARKELF_FIGHTER))),
                dynamicTest("initial start points for " + DARKELF_FIGHTER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DARKELF_FIGHTER).isEmpty())),

                dynamicTest("initial start points contains " + DARKELF_MAGICIAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(DARKELF_MAGICIAN))),
                dynamicTest("initial start points for " + DARKELF_MAGICIAN + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DARKELF_MAGICIAN).isEmpty())),

                dynamicTest("initial start points contains " + ORC_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(ORC_FIGHTER))),
                dynamicTest("initial start points for " + ORC_FIGHTER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ORC_FIGHTER).isEmpty())),

                dynamicTest("initial start points contains " + ORC_SHAMAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(ORC_SHAMAN))),
                dynamicTest("initial start points for " + ORC_SHAMAN + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ORC_SHAMAN).isEmpty())),

                dynamicTest("initial start points contains " + DWARF_APPRENTICE + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(DWARF_APPRENTICE))),
                dynamicTest("initial start points for " + DWARF_APPRENTICE + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DWARF_APPRENTICE).isEmpty())),

                dynamicTest("initial start points contains " + KAMAEL_M_SOLDIER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(KAMAEL_M_SOLDIER))),
                dynamicTest("initial start points for " + KAMAEL_M_SOLDIER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(KAMAEL_M_SOLDIER).isEmpty())),

                dynamicTest("initial start points contains " + KAMAEL_F_SOLDIER + " section",
                        () -> assumeTrue(settingsHolder.getInitialStartPoints().containsKey(KAMAEL_F_SOLDIER))),
                dynamicTest("initial start points for " + KAMAEL_F_SOLDIER + " contains at least one point",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(KAMAEL_F_SOLDIER).isEmpty()))
        );
    }

    /**
     * Assume that {@link SettingsHolder#initialEquipments} parsed correctly
     */
    @TestFactory
    public final Stream<DynamicTest> testParseInitialEquipments() {
        return Stream.of(
                dynamicTest("at least one initial equipments has been loaded",
                        () -> assumeFalse(settingsHolder.getInitialEquipments().isEmpty())),

                dynamicTest("initial equipments contains " + HUMAN_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(HUMAN_FIGHTER))),
                dynamicTest("initial equipments for " + HUMAN_FIGHTER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(HUMAN_FIGHTER).isEmpty())),

                dynamicTest("initial equipments contains " + HUMAN_MAGICIAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(HUMAN_MAGICIAN))),
                dynamicTest("initial equipments for " + HUMAN_MAGICIAN + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(HUMAN_MAGICIAN).isEmpty())),

                dynamicTest("initial equipments contains " + ELF_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(ELF_FIGHTER))),
                dynamicTest("initial equipments for " + ELF_FIGHTER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ELF_FIGHTER).isEmpty())),

                dynamicTest("initial equipments contains " + DARKELF_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(DARKELF_FIGHTER))),
                dynamicTest("initial equipments for " + DARKELF_FIGHTER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DARKELF_FIGHTER).isEmpty())),

                dynamicTest("initial equipments contains " + DARKELF_MAGICIAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(DARKELF_MAGICIAN))),
                dynamicTest("initial equipments for " + DARKELF_MAGICIAN + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DARKELF_MAGICIAN).isEmpty())),
                dynamicTest("initial equipments contains " + ORC_FIGHTER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(ORC_FIGHTER))),
                dynamicTest("initial equipments for " + ORC_FIGHTER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ORC_FIGHTER).isEmpty())),

                dynamicTest("initial equipments contains " + ORC_SHAMAN + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(ORC_SHAMAN))),
                dynamicTest("initial equipments for " + ORC_SHAMAN + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(ORC_SHAMAN).isEmpty())),

                dynamicTest("initial equipments contains " + DWARF_APPRENTICE + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(DWARF_APPRENTICE))),
                dynamicTest("initial equipments for " + DWARF_APPRENTICE + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(DWARF_APPRENTICE).isEmpty())),

                dynamicTest("initial equipments contains " + KAMAEL_M_SOLDIER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(KAMAEL_M_SOLDIER))),
                dynamicTest("initial equipments for " + KAMAEL_M_SOLDIER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(KAMAEL_M_SOLDIER).isEmpty())),

                dynamicTest("initial equipments contains " + KAMAEL_F_SOLDIER + " section",
                        () -> assumeTrue(settingsHolder.getInitialEquipments().containsKey(KAMAEL_F_SOLDIER))),
                dynamicTest("initial equipments for " + KAMAEL_F_SOLDIER + " contains at least one equipment",
                        () -> assumeFalse(settingsHolder.getInitialStartPoints().get(KAMAEL_F_SOLDIER).isEmpty()))
        );
    }

    /**
     * Assume that all item definition in initial equipment {@link SettingsHolder#initialEquipments}, contains in itemdata
     */
    @TestFactory
    public final Stream<DynamicTest> testInitialEquipmentHasPositiveCount() {
        return settingsHolder.getInitialEquipments().entrySet().stream()
                .map(entry ->
                        entry.getValue().entrySet().stream()
                                .map(equipment ->
                                        dynamicTest("initial equipment " + equipment.getKey() + " for class" + entry.getKey() + " exist in itemdata",
                                                () -> assumeTrue(
                                                        itemDatasHolder.getItemData().values().stream()
                                                                .anyMatch(itemData -> itemData.getName().equals(equipment.getKey())))
                                        )

                                )
                ).reduce(Stream::concat).orElse(Stream.empty());
    }
}