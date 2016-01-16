package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.packets.out.UserInfo;
import ru.jts_dev.gameserver.parser.impl.PCParametersHolder;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.time.GameTimeService;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.gameserver.parser.impl.PCParametersHolder.toPCParameterName;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@Opcode(0x11)
public class EnterWorld extends IncomingMessageWrapper {
    @Autowired
    private GameTimeService timeService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PCParametersHolder parametersData;

    @Override
    public void prepare() {
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 03.01.16 ItemList packet, ShortCutInit, BookMarkInfo, BasicAction, QuestList, EtcStatusUpdate, StorageMaxCount, FriendList,
        // TODO: 03.01.16 System Message : Welcome to Lineage, SkillCoolTime, ExVoteSystemInfo, Spawn player,
        // TODO: 03.01.16 HennaInfo, SkillList, broadcast CharInfo

        long gameTimeInMinutes = timeService.getGameTimeInMinutes();
        sessionService.send(session, new ClientSetTime(gameTimeInMinutes));

        // TODO: 04.01.16 broadcast CharInfo, send UserInfo
        // send UserInfo
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        String pcParameterName = toPCParameterName(character.getSex(), character.getStat().getClass_());
        assert parametersData.getCollisionBoxes().containsKey(pcParameterName);

        List<Double> collisions = parametersData.getCollisionBoxes().get(pcParameterName);

        sessionService.send(session, new UserInfo(character, collisions));
    }
}
