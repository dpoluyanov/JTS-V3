package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.time.GameTimeService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class EnterWorld extends IncomingMessageWrapper {
    private final GameSessionService sessionService;
    private final GameTimeService timeService;

    @Autowired
    public EnterWorld(GameSessionService sessionService, GameTimeService timeService) {
        this.sessionService = sessionService;
        this.timeService = timeService;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 03.01.16 ItemList packet, ShortCutInit, BookMarkInfo, BasicAction, QuestList, EtcStatusUpdate, StorageMaxCount, FriendList,
        // TODO: 03.01.16 System Message : Welcome to Lineage, SkillCoolTime, ExVoteSystemInfo, Spawn player

        long gameTimeInMinutes = timeService.getGameTimeInMinutes();
        session.send(new ClientSetTime(gameTimeInMinutes));
    }
}
