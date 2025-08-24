package lautadev.auth.service.Controller.customer;

import lautadev.auth.service.Entity.Account;
import lautadev.auth.service.Service.customer.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Account> myAccount() {
        return ResponseEntity.ok(accountService.findAccountByUserLogged());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.findAccountByAccountId(id));
    }
}
