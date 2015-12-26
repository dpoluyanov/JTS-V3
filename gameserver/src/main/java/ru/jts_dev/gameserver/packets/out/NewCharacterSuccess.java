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
        putInt(0x0D);
        putInt(recommendedStats.size()); // stats count

        for (int i = 0; i < maximumStats.size(); i++) {
            putInt(recommendedStats.get(i).getRaceId());
            putInt(recommendedStats.get(i).getClassId());

            // STR
            putInt(maximumStats.get(i).getForType(STR));
            putInt(recommendedStats.get(i).getForType(STR));
            putInt(minimumStats.get(i).getForType(STR));

            // DEX
            putInt(maximumStats.get(i).getForType(DEX));
            putInt(recommendedStats.get(i).getForType(DEX));
            putInt(minimumStats.get(i).getForType(DEX));

            // CON
            putInt(maximumStats.get(i).getForType(CON));
            putInt(recommendedStats.get(i).getForType(CON));
            putInt(minimumStats.get(i).getForType(CON));

            // INT
            putInt(maximumStats.get(i).getForType(INT));
            putInt(recommendedStats.get(i).getForType(INT));
            putInt(minimumStats.get(i).getForType(INT));

            // WIT
            putInt(maximumStats.get(i).getForType(WIT));
            putInt(recommendedStats.get(i).getForType(WIT));
            putInt(minimumStats.get(i).getForType(WIT));

            // WIT
            putInt(maximumStats.get(i).getForType(MEN));
            putInt(recommendedStats.get(i).getForType(MEN));
            putInt(minimumStats.get(i).getForType(MEN));
        }
    }
}
