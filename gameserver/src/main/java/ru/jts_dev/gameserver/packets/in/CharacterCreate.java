package ru.jts_dev.gameserver.packets.in;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.jts_dev.common.packets.IncomingMessageWrapper;
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.constants.CharacterRace;
import ru.jts_dev.gameserver.inventory.InventoryService;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.Opcode;
import ru.jts_dev.gameserver.packets.out.CharacterCreateSuccess;
import ru.jts_dev.gameserver.parser.data.CharacterStat;
import ru.jts_dev.gameserver.parser.data.item.ItemData;
import ru.jts_dev.gameserver.parser.data.item.ItemDatasHolder;
import ru.jts_dev.gameserver.parser.impl.SettingsHolder;
import ru.jts_dev.gameserver.repository.GameCharacterRepository;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.service.GameSessionService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

import static ru.jts_dev.gameserver.packets.out.CharacterCreateFail.*;

/**
 * @author Camelion
 * @since 20.12.15
 */
@Opcode(0x0C)
public class CharacterCreate extends IncomingMessageWrapper {
    private static final int MAX_CHARACTERS_ON_ACCOUNT = 7;

    @Autowired
    private GameSessionService sessionService;
    @Autowired
    private GameCharacterRepository characterRepository;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private SettingsHolder settingsData;
    @Autowired
    private ItemDatasHolder itemDatasHolder;
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private Validator validator;

    @Autowired
    private Random random;

    @Length(max = 16, message = REASON_16_ENG_CHARS)
    @Pattern(regexp = "[A-Za-z0-9]{4,16}", message = REASON_INCORRECT_NAME)
    private String name;
    @NotNull
    private CharacterRace race;
    @Range(min = 0, max = 1)
    private int sex;
    @NotNull
    private CharacterClass class_;

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
        race = readIntAs(CharacterRace.class); // race
        sex = readInt();
        class_ = readIntAs(CharacterClass.class); // class
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
        final GameSession session = sessionService.getSessionBy(getConnectionId());
        final String login = sessionService.getAccountBy(getConnectionId());
        final Set<ConstraintViolation<CharacterCreate>> errors = validator.validate(this);

        if (!errors.isEmpty()) {
            ConstraintViolation<CharacterCreate> error = errors.iterator().next();

            assert ERRORS.containsKey(error.getMessage()) : "Unknown error message " + error.getMessage();

            broadcastService.send(session, ERRORS.get(error.getMessage()));
        }
        // TODO: 25.12.15 validate race & class compatibility
        // TODO: 22.12.15 move this checks to our validator, or annotation with error message
        else if (characterRepository.existsByName(name)) {
            broadcastService.send(session, ERRORS.get(REASON_NAME_ALREADY_EXISTS));
        } else if (characterRepository.countByAccountName(login) >= MAX_CHARACTERS_ON_ACCOUNT) {
            broadcastService.send(session, ERRORS.get(REASON_TOO_MANY_CHARACTERS));
        } else {
            characterRepository.save(newCharacterWith(login));

            broadcastService.send(session, CharacterCreateSuccess.PACKET);
        }
    }

    private GameCharacter newCharacterWith(final String accountName) {
        // has initial equipments for this class
        assert settingsData.getInitialEquipments().containsKey(class_);

        // find stat or throw RuntimeException with stat not found exception
        final CharacterStat stat = (CharacterStat) settingsData.getRecommendedStats().stream().filter(
                st -> st.getRace() == race && st.getClass_() == class_)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Character stat for race " + race
                        + " and class " + class_ + " not found")).clone();

        // find initial start points
        final List<Vector3D> startPoints = settingsData.getInitialStartPoints().get(class_);

        final GameCharacter character = new GameCharacter();

        character.setAccountName(accountName);
        character.setName(name);
        character.setSex(sex);
        character.setHairStyle(hairStyle);
        character.setHairColor(hairColor);
        character.setFace(face);
        character.setStat(stat);

        final Vector3D startPoint = startPoints.get(random.nextInt(startPoints.size()));
        character.setVector3D(startPoint);
        character.setAngle(0);
        //character.setRotation(new Rotation(startPoint, 0.0D, VECTOR_OPERATOR));

        // find initial equipment, and add items to character
        final Map<String, Integer> initialEquipment = settingsData.getInitialEquipments().get(class_);
        final List<Integer> items = itemDatasHolder.getItemData().values()
                .parallelStream()
                .filter(itemData -> initialEquipment.containsKey(itemData.getName()))
                .mapToInt(ItemData::getItemId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        inventoryService.giveItems(character, items);

        return character;
    }
}
