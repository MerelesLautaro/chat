package lautadev.auth.service.DTO.authentication.request;

public record LoginRequest(String identifier,
                           String password) {
}
