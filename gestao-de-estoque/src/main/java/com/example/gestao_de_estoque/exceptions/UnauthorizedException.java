package com.example.gestao_de_estoque.exceptions;

/**
 * Exceção usada para indicar falta de autenticação do usuário.
 * Retorna status HTTP 401 (Unauthorized) quando tratada globalmente.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}