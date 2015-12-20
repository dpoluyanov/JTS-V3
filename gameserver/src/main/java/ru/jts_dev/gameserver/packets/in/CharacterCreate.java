package ru.jts_dev.gameserver.packets.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.CharacterCreateSuccess;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class CharacterCreate extends IncomingMessageWrapper {
    @Autowired
    private GameSessionService sessionService;

    private String name;
    private int raceId;
    private int sex;
    private int classId;
    private int _int;
    private int str;
    private int con;
    private int men;
    private int dex;
    private int wit;
    private int hairStyle;
    private int hairColor;
    private int face;

    @Override
    public void prepare() {
        name = readString();
        raceId = readInt(); // race
        sex = readInt();
        classId = readInt();
        _int = readInt(); // int
        str = readInt(); // str
        con = readInt(); // con
        men = readInt(); // men
        dex = readInt(); // dex
        wit = readInt(); // wit
        hairStyle = readInt();
        hairColor = readInt();
        face = readInt();
    }

    @Override
    public void run() {
        GameSession session = sessionService.getSessionBy(getConnectionId());

        // TODO: 20.12.15 additional check for username, race, sex, class, int, hairStyle, color and face with spring-validation

        session.send(new CharacterCreateSuccess());
    }
}
