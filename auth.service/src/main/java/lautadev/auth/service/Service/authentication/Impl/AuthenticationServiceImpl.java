package lautadev.auth.service.Service.authentication.Impl;

import io.jsonwebtoken.Claims;
import lautadev.auth.service.DTO.authentication.request.LoginRequest;
import lautadev.auth.service.DTO.authentication.request.RefreshTokenRequest;
import lautadev.auth.service.DTO.authentication.request.RegisterRequest;
import lautadev.auth.service.DTO.authentication.response.Token;
import lautadev.auth.service.Entity.Account;
import lautadev.auth.service.Exception.ApiException;
import lautadev.auth.service.Repository.AccountRepository;
import lautadev.auth.service.Security.JwtBlacklistManager;
import lautadev.auth.service.Service.authentication.AuthenticationService;
import lautadev.auth.service.Service.authentication.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtBlacklistManager jwtBlacklistManager;

    @Override
    @Transactional
    public Token register(RegisterRequest registerRequest) {
        validateEmail(registerRequest);

        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        Account account = createAccount(registerRequest,encodedPassword);

        String token = tokenService.generateToken(account);
        String refreshToken = tokenService.generateRefreshToken(account);

        return new Token(token,refreshToken);
    }

    @Override
    public Token login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.identifier(), loginRequest.password()));

        Account account = (Account) authentication.getPrincipal();

        String token = tokenService.generateToken(account);
        String refreshToken = tokenService.generateRefreshToken(account); // TODO: ¿si ya existe un refresh token que no expiro retorno uno nuevo?

        return new Token(token,refreshToken);
    }

    @Override
    public void logout(String token) {
        jwtBlacklistManager.addTokenToBlackList(token);
    }

    @Override
    public Token refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
        Claims claims = tokenService.extractAllClaims(refreshTokenRequest.token());

        String username = claims.getSubject();
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(ApiException::accountNotFound);

        if (tokenService.isTokenExpired(refreshTokenRequest.token())) {
            throw ApiException.refreshTokenExpired();
        }

        String newAccessToken = tokenService.generateToken(account);
        String newRefreshToken = tokenService.generateRefreshToken(account);

        return new Token(newAccessToken, newRefreshToken);
    }

    private void validateEmail(RegisterRequest registerRequest) {
        if (accountRepository.existsByEmailIgnoreCase(registerRequest.email())) {
            throw ApiException.emailAlreadyExist();
        }
    }

    private Account createAccount(RegisterRequest registerRequest, String encodedPassword) {
        UUID accountId = UUID.randomUUID();
        UUID userId = UUID.randomUUID(); // TODO: llama al microservice User para crear la cuenta

        Account account = Account.builder()
                .accountId(accountId)
                .email(registerRequest.email())
                .password(encodedPassword)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();

       account = accountRepository.save(account);
       return account;
    }
}
