package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.ClientSetTime;
import ru.jts_dev.gameserver.packets.out.UserInfo;
import ru.jts_dev.gameserver.parser.impl.PCParametersData;
import ru.jts_dev.gameserver.service.GameSessionService;
import ru.jts_dev.gameserver.service.PlayerService;
import ru.jts_dev.gameserver.time.GameTimeService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.gameserver.parser.impl.PCParametersData.toPCParameterName;

/**
 * @author Camelion
 * @since 03.01.16
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class EnterWorld extends IncomingMessageWrapper {
    @Autowired
    private GameTimeService timeService;
    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PCParametersData parametersData;

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
        session.send(new ClientSetTime(gameTimeInMinutes));


        // TODO: 04.01.16 broadcast CharInfo, send UserInfo
        // send UserInfo
        GameCharacter character = playerService.getCharacterBy(getConnectionId());

        String pcParameterName = toPCParameterName(character.getSex(), character.getStat().getStatName());
        assert parametersData.getCollisionBoxes().containsKey(pcParameterName);

        double[] collisions = parametersData.getCollisionBoxes().get(pcParameterName);

        session.send(new UserInfo(character, collisions));
    }
}
