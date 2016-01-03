package ru.jts_dev.gameserver.variables.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Repository
public interface ServerVariablesRepository extends CrudRepository<ServerVariable, ServerVariableKey> {
}
