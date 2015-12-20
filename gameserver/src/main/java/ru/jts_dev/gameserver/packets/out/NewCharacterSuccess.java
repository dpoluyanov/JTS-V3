package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import java.util.List;

import static ru.jts_dev.gameserver.parser.data.CharacterStat.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
public class NewCharacterSuccess extends OutgoingMessageWrapper {
    private final List<CharacterStat> maximumStats;
    private final List<CharacterStat> recommendedStats;
    private final List<CharacterStat> minimumStats;

    public NewCharacterSuccess(
            List<CharacterStat> maximumStats,
            List<CharacterStat> recommendedStats,
            List<CharacterStat> minimumStats) {
        this.maximumStats = maximumStats;
        this.recommendedStats = recommendedStats;
        this.minimumStats = minimumStats;
    }

    @Override
    public void write() {
        putInt(0x0C);
        putInt(recommendedStats.size()); // stats count

        for (int i = 0; i < maximumStats.size(); i++) {
            putInt(recommendedStats.get(i).getRaceId());
            putInt(recommendedStats.get(i).getClassId());

            // STR
            putInt(maximumStats.get(i).getStatForType(STR));
            putInt(recommendedStats.get(i).getStatForType(STR));
            putInt(minimumStats.get(i).getStatForType(STR));

            // DEX
            putInt(maximumStats.get(i).getStatForType(DEX));
            putInt(recommendedStats.get(i).getStatForType(DEX));
            putInt(minimumStats.get(i).getStatForType(DEX));

            // CON
            putInt(maximumStats.get(i).getStatForType(CON));
            putInt(recommendedStats.get(i).getStatForType(CON));
            putInt(minimumStats.get(i).getStatForType(CON));

            // INT
            putInt(maximumStats.get(i).getStatForType(INT));
            putInt(recommendedStats.get(i).getStatForType(INT));
            putInt(minimumStats.get(i).getStatForType(INT));

            // WIT
            putInt(maximumStats.get(i).getStatForType(WIT));
            putInt(recommendedStats.get(i).getStatForType(WIT));
            putInt(minimumStats.get(i).getStatForType(WIT));

            // WIT
            putInt(maximumStats.get(i).getStatForType(MEN));
            putInt(recommendedStats.get(i).getStatForType(MEN));
            putInt(minimumStats.get(i).getStatForType(MEN));
        }
    }
}
