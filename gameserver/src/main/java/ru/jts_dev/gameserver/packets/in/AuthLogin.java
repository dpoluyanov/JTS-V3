package ru.jts_dev.gameserver.packets.in;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class AuthLogin extends IncomingMessageWrapper {
    private String login;
    private int playKey1;
    private int playKey2;
    private int loginKey1;
    private int loginKey2;
    private int languageType;

    @Override
    public void prepare() {
        login = readString();
        playKey2 = readInt();
        playKey1 = readInt();
        loginKey1 = readInt();
        loginKey2 = readInt();
        languageType = readInt();
    }

    @Override
    public void run() {
        // TODO: 13.12.15 send character selection info
    }
}
