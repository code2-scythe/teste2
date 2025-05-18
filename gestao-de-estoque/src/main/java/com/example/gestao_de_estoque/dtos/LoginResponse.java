package com.example.gestao_de_estoque.dtos;

/**
 * DTO utilizado para retornar a resposta de login.
 * Contém apenas o token JWT gerado após a autenticação.
 */
public record LoginResponse(
        String token
) {
    // Nenhum método adicional necessário. A resposta carrega apenas o token.
}