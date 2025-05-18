package com.example.gestao_de_estoque.repositories;

import com.example.gestao_de_estoque.models.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório responsável por operações CRUD do Estabelecimento.
 */
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {

    /**
     * Busca um estabelecimento pelo e-mail.
     *
     * @param email e-mail do estabelecimento
     * @return um objeto Optional com o Estabelecimento encontrado, ou vazio se não encontrado
     */
    Optional<Estabelecimento> findByEmail(String email);
}