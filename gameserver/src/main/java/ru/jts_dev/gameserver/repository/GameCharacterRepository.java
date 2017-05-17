/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

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
     * Hits before {@link GameCharacterRepository#save(Object)}
     * sets lastUsed field false for all characters
     *
     * @param character - character, prepared to save
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE GameCharacter c SET c.lastUsed = false WHERE c.accountName = :#{#character.accountName}")
    void updateLastUsed(@Param("character") GameCharacter character);

    /**
     * @author Camelion
     * @since 30.07.16
     */
    @Aspect
    @Component
    class GameCharacterRepositoryAspects {
        private final GameCharacterRepository repository;

        @Autowired
        public GameCharacterRepositoryAspects(GameCharacterRepository repository) {
            this.repository = repository;
        }

        @Before("execution(* GameCharacterRepository+.save(..)) && args(character))")
        public void updateLastUsedAdvice(GameCharacter character) {
            character.setLastUsed(true);

            // perform update
            repository.updateLastUsed(character);
        }
    }
}
