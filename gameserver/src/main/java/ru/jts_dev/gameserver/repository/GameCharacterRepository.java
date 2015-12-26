package ru.jts_dev.gameserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
}
