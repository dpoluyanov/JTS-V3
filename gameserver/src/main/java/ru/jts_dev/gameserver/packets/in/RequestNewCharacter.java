package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.NewCharacterSuccess;
import ru.jts_dev.gameserver.parser.impl.SettingsHolder;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

/**
 * @author Camelion
 * @since 14.12.15
 */
@Opcode(0x13)
public class RequestNewCharacter extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private SettingsHolder settingsData;

    @Override
    public void prepare() {
        // no data
    }

    @Override
    public void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 14.12.15 connection close packet, if new character creation disabled for this server
        broadcastService.send(session, new NewCharacterSuccess(settingsData.getMaximumStats(),
                settingsData.getRecommendedStats(), settingsData.getMinimumStats()));
    }
}
