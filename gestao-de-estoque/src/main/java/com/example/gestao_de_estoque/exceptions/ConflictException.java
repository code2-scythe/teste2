package com.example.gestao_de_estoque.exceptions;

/**
 * Exceção usada para indicar conflitos de dados, como registros duplicados.
 * Retorna status HTTP 409 (Conflict) quando tratada globalmente.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}