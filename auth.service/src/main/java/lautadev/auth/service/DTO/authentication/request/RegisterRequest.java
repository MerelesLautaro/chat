package lautadev.auth.service.DTO.authentication.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lautadev.auth.service.Util.Password;

public record RegisterRequest(@NotBlank @Email(message = "Invalid email: ${validatedValue}") String email,
                              @Password String password) {
}
