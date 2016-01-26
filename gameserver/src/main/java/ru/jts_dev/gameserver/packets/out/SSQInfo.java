package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * Seven Signs Info
 * <p>
 * packet id 0x73
 * format: cc
 * <p>
 * Пример пакета с оффа (828 протокол):
 * 73 01 01
 * <p>
 * Возможные варианты использования данного пакета:
 * 0 0 - Обычное небо???
 * 1 1 - Dusk Sky
 * 2 2 - Dawn Sky???
 * 3 3 - Небо постепенно краснеет (за 10 секунд)
 * <p>
 * Возможно и другие вариации, эффект не совсем понятен.
 * 1 0
 * 0 1
 *
 * @author Java-man
 * @since 26.01.2016
 */
public class SSQInfo extends OutgoingMessageWrapper {
    public static final SSQInfo NOTHING = new SSQInfo(0);

    private int state;

    private SSQInfo(int state) {
        this.state = state;
    }

    @Override
    public void write() {
        writeByte(0x73);
        writeShort(256 + state);
    }
}
