package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.packets.out.ExBasicActionList;
import ru.jts_dev.gameserver.packets.out.UserInfo;
import ru.jts_dev.gameserver.parser.data.action.Action;
import ru.jts_dev.gameserver.parser.impl.PcParametersHolder;
import ru.jts_dev.gameserver.parser.impl.UserBasicActionsHolder;
import ru.jts_dev.gameserver.service.BroadcastServiceTemp;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.time.GameTimeService;

import java.util.Collection;
import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.gameserver.parser.impl.PcParametersHolder.toPCParameterName;

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
    private BroadcastServiceTemp broadcastService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PcParametersHolder parametersData;
    @Autowired
    private UserBasicActionsHolder userBasicActionsHolder;

    @Override
    public void prepare() {
        // Do nothing
    }

    @Override
    public void run() {
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        // TODO: 03.01.16 ItemList packet, ShortCutInit, BookMarkInfo, QuestList, EtcStatusUpdate, StorageMaxCount, FriendList,
        // TODO: 03.01.16 System Message : Welcome to Lineage, SkillCoolTime, ExVoteSystemInfo, Spawn player,
        // TODO: 03.01.16 HennaInfo, SkillList, broadcast CharInfo

        Collection<Action> actions = userBasicActionsHolder.getActionsData().values();
        broadcastService.send(character, new ExBasicActionList(actions));

        long gameTimeInMinutes = timeService.getGameTimeInMinutes();
        broadcastService.send(character, new ClientSetTime(gameTimeInMinutes));

        // TODO: 04.01.16 broadcast CharInfo, send UserInfo
        // send UserInfo

        String pcParameterName = toPCParameterName(character.getSex(), character.getStat().getClass_());
        assert parametersData.getCollisionBoxes().containsKey(pcParameterName);

        List<Double> collisions = parametersData.getCollisionBoxes().get(pcParameterName);

        broadcastService.send(character, new UserInfo(character, collisions));
    }
}
