package ru.jts_dev.gameserver.parser.data.npc;

import ru.jts_dev.gameserver.constants.NpcType;

/**
 * This class designed to be immutable, thread safe npc template.
 * Instances of this class can be shared across thread without any limitations.
 *
 * @author Camelion
 * @since 16.02.16
 */
public final class NpcData {
    private final int npcId;
    private final NpcType npcType;
    private final String name;

    public NpcData(NpcType npcType, int npcId, String name) {
        this.npcId = npcId;
        this.npcType = npcType;
        this.name = name;
    }

    public int getNpcId() {
        return npcId;
    }

    public NpcType getNpcType() {
        return npcType;
    }

    public String getName() {
        return name;
    }
}
