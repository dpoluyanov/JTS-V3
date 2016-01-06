package ru.jts_dev.gameserver.packets.in;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(second = 0x01)
public class RequestManorList extends IncomingMessageWrapper {
    @Override
    public void prepare() {

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not released yet");
    }
}
