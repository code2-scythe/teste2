package com.example.gestao_de_estoque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração de segurança da aplicação.
 * Configura a proteção HTTP, controle de sessão e codificação de senhas.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura a segurança para as requisições HTTP.
     * Desabilita CSRF, permite todas as requisições e define o gerenciamento de sessões como sem estado (stateless).
     *
     * @param http o objeto HttpSecurity para configurar as regras de segurança
     * @return a configuração do filtro de segurança
     * @throws Exception se ocorrer algum erro ao configurar a segurança
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Define o gerenciamento de sessão como sem estado

        return http.build();
    }

    /**
     * Configura o codificador de senhas.
     * Utiliza o algoritmo BCrypt para codificar senhas.
     *
     * @return o codificador de senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna uma instância do codificador BCrypt
    }
}