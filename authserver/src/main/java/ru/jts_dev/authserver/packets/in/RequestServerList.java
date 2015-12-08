package ru.jts_dev.authserver.packets.in;

import ru.jts_dev.authserver.packets.IncomingMessageWrapper;

/**
 * @author Camelion
 * @since 08.12.15
 */
public class RequestServerList extends IncomingMessageWrapper {
    private int key1, key2;

    @Override
    public void prepare() {
        key1 = readInt();
        key2 = readInt();
    }

    @Override
    public void run() {
        // TODO: 08.12.15 check key1, key2 with keys, sended in LoginOk
        // TODO: 08.12.15 Send ServerList packet

    }
}
