package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.NewCharacterSuccess;
import ru.jts_dev.gameserver.parser.impl.SettingsData;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 14.12.15
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x13)
public class RequestNewCharacter extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;

    @Autowired
    private SettingsData settingsData;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 14.12.15 connection close packet, if new character creation disabled for this server
        session.send(new NewCharacterSuccess(settingsData.getMaximumStats(),
                settingsData.getRecommendedStats(), settingsData.getMinimumStats()));
    }
}
