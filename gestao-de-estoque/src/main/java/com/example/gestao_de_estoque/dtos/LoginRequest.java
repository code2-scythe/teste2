package com.example.gestao_de_estoque.dtos;

import com.example.gestao_de_estoque.models.Estabelecimento;

/**
 * DTO utilizado para requisições de login.
 * Contém apenas os campos necessários para autenticação: email e senha.
 */
public record LoginRequest(
        String email,
        String senha
) {
    /**
     * Atualiza os campos de e-mail e senha da entidade Estabelecimento.
     *
     * @param est Instância da entidade Estabelecimento
     * @return Entidade atualizada com os dados de autenticação
     */
    public Estabelecimento toEstabelecimento(Estabelecimento est) {
        est.setEmail(email());
        est.setSenha(senha());
        return est;
    }
}