package ru.jts_dev.gameserver.packets.in;

import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.CharacterCreateSuccess;
import ru.jts_dev.gameserver.parser.data.CharacterStat;
import ru.jts_dev.gameserver.parser.impl.SettingsData;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.GameSessionService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static ru.jts_dev.gameserver.packets.out.CharacterCreateFail.*;
import static ru.jts_dev.gameserver.parser.data.CharacterStat.RACE_HUMAN;
import static ru.jts_dev.gameserver.parser.data.CharacterStat.RACE_KAMAEL;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
public class CharacterCreate extends IncomingMessageWrapper {
    private static final Logger log = LoggerFactory.getLogger(CharacterCreate.class);

    private static final int MAX_CHARACTERS_ON_ACCOUNT = 7;

    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private GameCharacterRepository characterRepository;

    @Autowired
    private SettingsData settingsData;

    @Autowired
    private Validator validator;

    @Pattern(regexp = "[A-Za-z0-9]{4,16}", message = REASON_16_ENG_CHARS)
    private String name;
    @Range(min = RACE_HUMAN, max = RACE_KAMAEL)
    private int raceId;
    @Range(min = 0, max = 1)
    private int sex;
    // TODO: 26.12.15 move to our validator
    /*
    @Digits.List({
            @Digits(integer = CLASS_HUMAN_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_HUMAN_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_SHAMAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DWARF_APPRENTICE, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
    })*/
    private int classId;

    // not validated
    private int _int;
    private int str;
    private int con;
    private int men;
    private int dex;
    private int wit;

    // TODO: 22.12.15 max = 4 for sex = 0
    @Range(min = 0, max = 6, message = REASON_CREATION_FAILED)
    private int hairStyle;
    @Range(min = 0, max = 3, message = REASON_CREATION_FAILED)
    private int hairColor;
    @Range(min = 0, max = 2, message = REASON_CREATION_FAILED)
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

        String login = sessionService.getAccountBy(getConnectionId());

        Set<ConstraintViolation<CharacterCreate>> errors = validator.validate(this);

        if (!errors.isEmpty()) {
            ConstraintViolation<CharacterCreate> error = errors.iterator().next();

            assert ERRORS.containsKey(error.getMessage()) : "Unknown error message " + error.getMessage();

            session.send(ERRORS.get(error.getMessage()));
        }
        // TODO: 25.12.15 validate race & class compatibility
        // TODO: 22.12.15 move this checks to our validator, or annotation with error message
        else if (characterRepository.existsByName(name)) {
            session.send(ERRORS.get(REASON_NAME_ALREADY_EXISTS));
        } else if (characterRepository.countByAccountName(login) >= MAX_CHARACTERS_ON_ACCOUNT) {
            session.send(ERRORS.get(REASON_TOO_MANY_CHARACTERS));
        } else {
            // find stat or throw RuntimeException with stat not found exception
            CharacterStat stat = settingsData.getRecommendedStats().stream().filter(
                    s -> s.getRaceId() == raceId && s.getClassId() == s.getClassId())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Character stat for race " + raceId
                            + " and class " + classId + " not found"));

            characterRepository.save(newCharacterWith(stat, login));

            session.send(new CharacterCreateSuccess());
        }
    }

    private GameCharacter newCharacterWith(CharacterStat stat, String accountName) {
        GameCharacter character = new GameCharacter();

        character.setAccountName(accountName);
        character.setName(name);
        character.setRaceId(raceId);
        character.setSex(sex);
        character.setClassId(classId);
        character.setHairStyle(hairStyle);
        character.setHairColor(hairColor);
        character.setFace(face);

        return character;
    }
}
