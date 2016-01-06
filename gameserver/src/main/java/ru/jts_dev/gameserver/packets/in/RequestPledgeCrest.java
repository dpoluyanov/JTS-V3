package ru.jts_dev.gameserver.packets.in;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.packets.Opcode;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x67)
public class RequestPledgeCrest extends IncomingMessageWrapper {
    private int crestId;

    @Override
    public void prepare() {
        crestId = readInt();
    }

    @Override
    public void run() {
        // todo send crest id
    }
}
