package lautadev.auth.service.Repository;

import lautadev.auth.service.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<Account> findByEmail(String username);

    Account getReferenceByEmailIgnoreCase(String email);

    Optional<Account> findAccountByAccountId(UUID accountId);
}
