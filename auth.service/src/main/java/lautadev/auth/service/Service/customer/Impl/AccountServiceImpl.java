package lautadev.auth.service.Service.customer.Impl;

import lautadev.auth.service.Entity.Account;
import lautadev.auth.service.Exception.ApiException;
import lautadev.auth.service.Repository.AccountRepository;
import lautadev.auth.service.Service.customer.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService,UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() ->
                        new ApiException("User not found for the given identifier: " + username,
                                new UsernameNotFoundException(username), HttpStatus.BAD_REQUEST));
    }

    @Override
    public Account findAccountByAccountId(UUID accountId) {
        return accountRepository.findAccountByAccountId(accountId)
                .orElseThrow(ApiException::accountNotFound);
    }

    @Override
    public Account findAccountByUserLogged() {
        return getUserLogged();
    }

    private Account getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Account) authentication.getPrincipal();
    }
}
