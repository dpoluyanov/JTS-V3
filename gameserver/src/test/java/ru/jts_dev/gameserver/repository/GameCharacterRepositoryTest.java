package ru.jts_dev.gameserver.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.constants.CharacterRace;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Camelion
 * @since 28.12.15
 */
@Rollback
@DataJpaTest
@EntityScan(basePackages = "ru.jts_dev.gameserver")
@EnableAspectJAutoProxy
@TestPropertySource(properties = "logging.level.org.hibernate.type=trace")
@SpringJUnitConfig(classes = {GameCharacterRepository.class, GameCharacterRepository.GameCharacterRepositoryAspects.class})
public class GameCharacterRepositoryTest {

    @Autowired
    private GameCharacterRepository repository;

    @Test
    @DisplayName("exists by name")
    public void testExistsByName() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setName("Test1");
        repository.save(character);

        assertTrue(repository.existsByName("Test1"));
        assertFalse(repository.existsByName("BadName"));
    }

    @Test
    @DisplayName("count by account name")
    public void testCountByAccountName() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        assertTrue(repository.countByAccountName("Test") == 2);
        assertTrue(repository.countByAccountName("BadName") == 0);
    }

    @Test
    @DisplayName("find all by account name")
    public void testFindAllByAccountName() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        character.setName("killer");
        repository.save(character);

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        character.setName("bruce");
        repository.save(character);

        final List<GameCharacter> list = repository.findAllByAccountName("Test");
        assertAll("all accounts has been present",
                () -> assertTrue(list.stream().anyMatch(ch -> ch.getName().equals("killer"))),
                () -> assertTrue(list.stream().anyMatch(ch -> ch.getName().equals("bruce"))),
                () -> assertTrue(list.size() == 2)
        );

        assertTrue(repository.findAllByAccountName("BadAccName").isEmpty(), "not existing account name finds nothing");
    }

    @Test
    @DisplayName("test last used updated for one character")
    public void testUpdateLastUsed() {
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        List<GameCharacter> list = repository.findAllByAccountName("Test");

        assertTrue(list.stream().anyMatch(GameCharacter::isLastUsed), "last used should be updated");
    }

    @Test
    @DisplayName("test last used was updated for all characters")
    public void testUpdateLastUsedForAll() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        // ready
        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        // go
        List<GameCharacter> list = repository.findAllByAccountName("Test");

        assertAll("last used updates all characters on account",
                () -> assertTrue(list.stream().anyMatch(ch -> ch.isLastUsed()), "should have character with lastUsed = true"),
                () -> assertTrue(list.stream().anyMatch(ch -> !ch.isLastUsed()), "should have character with lastUsed = false")
        );
    }
}