package ru.jts_dev.gameserver.packets.out.movement;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

public class ExRotation extends OutgoingMessageWrapper {
    private final int charObjId;
    private final int degree;

    public ExRotation(int charId, int degree) {
        charObjId = charId;
        this.degree = degree;
    }

    @Override
    public void write() {
        writeByte(0xFE);
        writeShort(0xC1);
        writeInt(charObjId);
        writeInt(degree);
    }
}
