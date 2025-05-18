package com.example.gestao_de_estoque.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilitário para geração e validação de tokens JWT.
 */
@Component
public class JwtUtil {

    // Chave secreta gerada aleatoriamente
    private static final String SECRET_KEY = "WMExZ5NYgFhQZFj2YADNNDkDZGIzEZV00=TZZjzMZZw5ZhhjZgg1whjDYkzYjyjj3EZTZMDgMGkD";

    // Tempo de expiração do token (1 dia)
    private static final long EXPIRATION_MS = 86_400_000;

    // Criação de uma chave segura com o método adequado
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Gera um token JWT com o e-mail como subject.
     *
     * @param email e-mail do usuário para ser inserido no token
     * @return o token JWT gerado
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    /**
     * Valida o token JWT.
     *
     * @param token o token JWT a ser validado
     * @return verdadeiro se o token for válido, falso caso contrário
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Extrai o e-mail (subject) do token JWT.
     *
     * @param token o token JWT do qual o e-mail será extraído
     * @return o e-mail do usuário contido no token
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}