package com.mouramath.todolistapi.infrastructure.exception;

public class JwtTokenException extends RuntimeException {



    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(String messsage, Throwable cause) {
        super(messsage, cause);
    }

    public static JwtTokenException invalidToken(String reason, Throwable cause) {
        return new JwtTokenException("Token inválido: " + reason, cause);
    }

    public static JwtTokenException expiredToken(Throwable cause) {
        return new JwtTokenException("Token expirado", cause);
    }

}
