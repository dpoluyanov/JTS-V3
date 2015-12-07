package ru.jts_dev.authserver.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jts_dev.authserver.model.Account;

/**
 * @author Camelion
 * @since 08.12.15
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
