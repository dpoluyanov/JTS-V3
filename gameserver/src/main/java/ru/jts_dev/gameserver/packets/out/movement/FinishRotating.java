package ru.jts_dev.gameserver.packets.out.movement;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

public class FinishRotating extends OutgoingMessageWrapper {
    private final int charId;
    private final int degree;
    private final int speed;

    public FinishRotating(GameCharacter player, int degree, int speed) {
        charId = player.getObjectId();
        this.degree = degree;
        this.speed = speed;
    }

    @Override
    public final void write() {
        writeByte(0x61);

        writeInt(charId);
        writeInt(degree);
        writeInt(speed);
        writeByte(0x00); // TODO
    }
}