package lautadev.auth.service.Controller.authentication;

import jakarta.validation.Valid;
import lautadev.auth.service.DTO.authentication.GenericResponse;
import lautadev.auth.service.DTO.authentication.request.LoginRequest;
import lautadev.auth.service.DTO.authentication.request.RefreshTokenRequest;
import lautadev.auth.service.DTO.authentication.request.RegisterRequest;
import lautadev.auth.service.DTO.authentication.response.Token;
import lautadev.auth.service.Service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse> logout(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                                      String authorization) {
        authenticationService.logout(authorization);
        return ResponseEntity.ok(new GenericResponse("logout successfully"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Token> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshAccessToken(refreshTokenRequest));
    }
}
