package com.mouramath.todolistapi.infrastructure.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;



@Slf4j
@ControllerAdvice
public class JwtExceptionHandler {


    //Manipula exceções personalizadas de JWT da aplicação.

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenException(JwtTokenException ex, WebRequest request) {
        log.error("Erro de autenticação JWT personalizado: {}", ex.getMessage());
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Falha de autenticação",
                ex.getMessage(),
                request.getDescription(false)
        );
    }


     // Manipula exceções de assinatura JWT inválida.

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex, WebRequest request) {
        log.error("Assinatura JWT inválida: {}", ex.getMessage());
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Assinatura inválida",
                "O token tem uma assinatura inválida",
                request.getDescription(false)
        );
    }

    // Manipula exceções de token JWT expirado.

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        log.error("Token JWT expirado: {}", ex.getMessage());
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Token expirado",
                "O token de acesso expirou",
                request.getDescription(false)
        );
    }


     // Manipula exceções de token JWT malformado.

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex, WebRequest request) {
        log.error("Token JWT malformado: {}", ex.getMessage());
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Token inválido",
                "O formato do token está incorreto",
                request.getDescription(false)
        );
    }


     // Manipula exceções de token JWT não suportado.

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException ex, WebRequest request) {
        log.error("Token JWT não suportado: {}", ex.getMessage());
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Token não suportado",
                "O token contém um formato não suportado",
                request.getDescription(false)
        );
    }


     //Method auxiliar para criar uma resposta de erro padronizada.

    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path) {

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                error,
                message,
                path,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
