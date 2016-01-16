package ru.jts_dev.gameserver.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.gameserver.GameServerApplication;
import ru.jts_dev.gameserver.constants.CharacterClass;
import ru.jts_dev.gameserver.constants.CharacterRace;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * @author Camelion
 * @since 28.12.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameServerApplication.class)
// TODO: 02.01.16 migrate @SpringApplicationConfiguration to @ContextConfiguration (currently not work Aspect injection points)
//@ContextConfiguration(classes = {DatabaseTestConfig.class})
public class GameCharacterRepositoryTest extends Assert {

    @Autowired
    private GameCharacterRepository repository;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testExistsByName() throws Exception {
        // ready
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setName("Test1");
        repository.save(character);

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setName("Test2");
        repository.save(character);

        // go
        assertTrue(repository.existsByName("Test1"));
        assertTrue(repository.existsByName("Test2"));

        assertFalse(repository.existsByName("Test"));
        assertFalse(repository.existsByName("t"));
        assertFalse(repository.existsByName("test1"));
        assertFalse(repository.existsByName("test2"));
    }

    @Test
    public void testCountByAccountName() throws Exception {
        // ready
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test2");
        repository.save(character);

        // go
        assertThat(repository.countByAccountName("Test"), is(2));
        assertThat(repository.countByAccountName("Test2"), is(1));
        assertThat(repository.countByAccountName("test"), is(0));
        assertThat(repository.countByAccountName("NotExistAcc"), is(0));
    }

    @Test
    public void testFindAllByAccountName() throws Exception {
        // ready
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

        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test2");
        character.setName("leena");
        repository.save(character);

        // go
        List<GameCharacter> list = repository.findAllByAccountName("Test");
        assertThat(list, hasSize(2));
        assertThat(list, hasItem(hasProperty("name", equalTo("killer"))));
        assertThat(list, hasItem(hasProperty("name", equalTo("bruce"))));

        list = repository.findAllByAccountName("Test2");
        assertThat(list, hasSize(1));
        assertThat(list, hasItem(hasProperty("name", equalTo("leena"))));

        assertThat(repository.findAllByAccountName("EmptyAcc"), is(empty()));
    }

    @Test
    public void testUpdateLastUsed() throws Exception {
        // ready
        GameCharacter character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        // go
        List<GameCharacter> list = repository.findAllByAccountName("Test");
        assertThat(list, hasItem(hasProperty("lastUsed", equalTo(true))));

        // ready
        character = new GameCharacter();
        character.setStat(new CharacterStat(CharacterRace.HUMAN, CharacterClass.HUMAN_FIGHTER, new ArrayList<>(6)));
        character.setAccountName("Test");
        repository.save(character);

        // go
        list = repository.findAllByAccountName("Test");

        assertThat(list, hasItem(hasProperty("lastUsed", equalTo(true))));
        assertThat(list, hasItem(hasProperty("lastUsed", equalTo(false))));
    }
}