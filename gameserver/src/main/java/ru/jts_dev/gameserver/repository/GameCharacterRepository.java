package ru.jts_dev.gameserver.repository;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.List;

/**
 * @author Camelion
 * @since 21.12.15
 */
@Repository
public interface GameCharacterRepository extends CrudRepository<GameCharacter, Integer> {
    /**
     * check existing character with given name
     *
     * @param name - name of character
     * @return - {@code true}, if character present in database, otherwise {@code false}
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM GameCharacter c WHERE c.name = :name")
    boolean existsByName(@Param("name") String name);

    /**
     * return count of characters on account with given login
     *
     * @param accountName - login name of account
     * @return - characters count
     */
    int countByAccountName(String accountName);


    /**
     * Find characters for given account name
     *
     * @param accountName - name of player account
     * @return - {@code List} with characters for given account name
     */
    List<GameCharacter> findAllByAccountName(String accountName);

    /**
     * Hit before {@link GameCharacterRepository#save(Object)}
     * set lastUsed field false for all accounts
     *
     * @param character - character, prepared to save
     */
    @Modifying
    @Transactional
    @Query("UPDATE GameCharacter c SET c.lastUsed = false WHERE c.accountName = ?#{#character.accountName}")
    void updateLastUsed(@Param("character") GameCharacter character);

    @Aspect
    @Component
    class Aspects {
        @Autowired
        private GameCharacterRepository repository;

        @Before("execution(* GameCharacterRepository+.save(..)) && args(character))")
        public void updateLastUsedAdvice(GameCharacter character) {
            character.setLastUsed(true);

            // perform update
            repository.updateLastUsed(character);
        }
    }
}
