package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.parser.data.action.Action;

import java.util.Collection;

/**
 * @author Java-man
 * @since 19.01.2016
 */
public class ExBasicActionList extends OutgoingMessageWrapper {
    private final Collection<Action> actions;

    public ExBasicActionList(Collection<Action> actions) {
        this.actions = actions;
    }

    @Override
    public void write() {
        putByte(0xFE);
        putShort(0x5F);

        putInt(actions.size());
        for (Action action : actions) {
            putInt(action.getId());
        }
    }
}
