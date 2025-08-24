package lautadev.auth.service.Util;

import java.util.Set;

public class Constants {

    private Constants() {
    }

    public static final Set<String> UNPROTECTED_PATHS = Set.of(
            "api/v1/authentication/register",
            "api/v1/authentication/login",
            "api/v1/authentication/logout",
            "api/v1/authentication/refresh-token"
    );
}
