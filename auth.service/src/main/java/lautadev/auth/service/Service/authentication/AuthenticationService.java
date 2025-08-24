package lautadev.auth.service.Service.authentication;

import lautadev.auth.service.DTO.authentication.request.LoginRequest;
import lautadev.auth.service.DTO.authentication.request.RefreshTokenRequest;
import lautadev.auth.service.DTO.authentication.request.RegisterRequest;
import lautadev.auth.service.DTO.authentication.response.Token;

public interface AuthenticationService {
    Token register(RegisterRequest registerRequest);
    Token login(LoginRequest loginRequest);
    void logout(String token);
    Token refreshAccessToken(RefreshTokenRequest refreshTokenRequest);
}
