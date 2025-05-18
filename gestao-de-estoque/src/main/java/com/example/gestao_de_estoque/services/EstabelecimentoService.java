package com.example.gestao_de_estoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço para operações de Estabelecimento, incluindo operações relacionadas à criptografia de senha.
 */
@Service
public class EstabelecimentoService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor da classe, que recebe a dependência do PasswordEncoder.
     *
     * @param passwordEncoder a instância de PasswordEncoder para criptografar senhas
     */
    @Autowired
    public EstabelecimentoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Criptografa a senha usando o algoritmo BCrypt.
     *
     * @param senha a senha em texto claro a ser criptografada
     * @return a senha criptografada
     */
    public String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha); // Criptografa a senha usando BCrypt
    }
}