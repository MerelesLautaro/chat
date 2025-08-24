package lautadev.auth.service.Service.customer;

import lautadev.auth.service.Entity.Account;

import java.util.UUID;

public interface AccountService {
    Account findAccountByAccountId(UUID accountId);
    Account findAccountByUserLogged();
}
