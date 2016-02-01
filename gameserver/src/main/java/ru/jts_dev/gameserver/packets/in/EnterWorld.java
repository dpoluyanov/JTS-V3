package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.inventory.InventoryService;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameItem;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.packets.out.ExBasicActionList;
import ru.jts_dev.gameserver.packets.out.ItemList;
import ru.jts_dev.gameserver.packets.out.UserInfo;
import ru.jts_dev.gameserver.parser.data.action.Action;
import ru.jts_dev.gameserver.parser.impl.PcParametersHolder;
import ru.jts_dev.gameserver.parser.impl.UserBasicActionsHolder;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.time.GameTimeService;

import java.util.Collection;
import java.util.List;

import static ru.jts_dev.gameserver.parser.impl.PcParametersHolder.toPCParameterName;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Opcode(0x11)
public class EnterWorld extends IncomingMessageWrapper {
    @Autowired
    private GameTimeService timeService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PcParametersHolder parametersData;
    @Autowired
    private UserBasicActionsHolder userBasicActionsHolder;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public void prepare() {
        // Do nothing
    }

    @Override
    public void run() {
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final GameCharacter character = playerService.getCharacterBy(getConnectionId());

        // TODO: 03.01.16 ExQuestItemList packet, ShortCutInit, BookMarkInfo, BasicAction, QuestList, EtcStatusUpdate, StorageMaxCount, FriendList,
        // TODO: 03.01.16 System Message : Welcome to Lineage, SkillCoolTime, ExVoteSystemInfo, Spawn player,
        // TODO: 03.01.16 HennaInfo, SkillList, broadcast CharInfo

        final Collection<Action> actions = userBasicActionsHolder.getActionsData().values();
        broadcastService.send(session, new ExBasicActionList(actions));

        final long minutesPassed = timeService.minutesPassed();
        broadcastService.send(session, new ClientSetTime((int) minutesPassed));

        final String pcParameterName = toPCParameterName(character.getSex(), character.getStat().getClass_());
        assert parametersData.getCollisionBoxes().containsKey(pcParameterName);

        final List<Double> collisions = parametersData.getCollisionBoxes().get(pcParameterName);

        final List<GameItem> commonItems = InventoryService.getCommonItemsFrom(character);
        broadcastService.send(session, new ItemList(commonItems, true));

        // TODO: 04.01.16 broadcast CharInfo
        broadcastService.send(session, new UserInfo(character, collisions));
    }
}
