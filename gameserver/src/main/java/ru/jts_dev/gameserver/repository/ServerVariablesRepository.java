package ru.jts_dev.gameserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jts_dev.gameserver.variables.server.ServerVariable;
import ru.jts_dev.gameserver.variables.server.ServerVariableKey;

/**
 * @author Java-man
 * @since 04.01.2016
 */
@Repository
public interface ServerVariablesRepository extends CrudRepository<ServerVariable, ServerVariableKey> {
}
