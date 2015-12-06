package ru.jts_dev.authserver.packets.in;

import ru.jts_dev.authserver.packets.IncomingMessageWrapper;

/**
 * @author Camelion
 * @since 06.12.15
 */
public class AuthGameGuard extends IncomingMessageWrapper {
    private int sessionId;

    @Override
    public void prepare() {
        sessionId = readInt();
    }

    @Override
    public void run() {

    }
}
