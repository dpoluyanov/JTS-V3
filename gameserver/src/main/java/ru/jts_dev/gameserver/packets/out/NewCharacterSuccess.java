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
        writeInt(0x0D);
        writeInt(recommendedStats.size()); // stats count

        for (int i = 0; i < maximumStats.size(); i++) {
            writeInt(recommendedStats.get(i).getRace().getId());
            writeInt(recommendedStats.get(i).getClass_().getId());

            // STR
            writeInt(maximumStats.get(i).getForType(STR));
            writeInt(recommendedStats.get(i).getForType(STR));
            writeInt(minimumStats.get(i).getForType(STR));

            // DEX
            writeInt(maximumStats.get(i).getForType(DEX));
            writeInt(recommendedStats.get(i).getForType(DEX));
            writeInt(minimumStats.get(i).getForType(DEX));

            // CON
            writeInt(maximumStats.get(i).getForType(CON));
            writeInt(recommendedStats.get(i).getForType(CON));
            writeInt(minimumStats.get(i).getForType(CON));

            // INT
            writeInt(maximumStats.get(i).getForType(INT));
            writeInt(recommendedStats.get(i).getForType(INT));
            writeInt(minimumStats.get(i).getForType(INT));

            // WIT
            writeInt(maximumStats.get(i).getForType(WIT));
            writeInt(recommendedStats.get(i).getForType(WIT));
            writeInt(minimumStats.get(i).getForType(WIT));

            // WIT
            writeInt(maximumStats.get(i).getForType(MEN));
            writeInt(recommendedStats.get(i).getForType(MEN));
            writeInt(minimumStats.get(i).getForType(MEN));
        }
    }
}
