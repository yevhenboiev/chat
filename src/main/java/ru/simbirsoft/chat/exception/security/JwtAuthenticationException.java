package ru.simbirsoft.chat.exception.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException() {
        super("JWT token is expired on invalid");
    }

}
