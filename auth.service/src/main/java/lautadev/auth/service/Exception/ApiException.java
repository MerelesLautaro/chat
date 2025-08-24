package lautadev.auth.service.Exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ApiException emailAlreadyExist() { return new ApiException("Email already exist", HttpStatus.BAD_REQUEST);}

    public static ApiException accountNotFound() { return new ApiException("Account not found", HttpStatus.BAD_REQUEST);}

    public static ApiException refreshTokenExpired() { return new ApiException("Refresh token expired", HttpStatus.UNAUTHORIZED);}

    public static ApiException accessDenied() {
        return new ApiException("Access Denied", HttpStatus.UNAUTHORIZED);
    }

}