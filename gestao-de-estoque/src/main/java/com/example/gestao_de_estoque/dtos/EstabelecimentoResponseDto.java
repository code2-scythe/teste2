package com.example.gestao_de_estoque.dtos;

import com.example.gestao_de_estoque.models.Estabelecimento;
import java.time.LocalDate;

/**
 * DTO utilizado para retornar dados públicos de um Estabelecimento ao cliente.
 */
public record EstabelecimentoResponseDto(
        Integer idEstabelecimento,
        String nomeFantasia,
        String telefone,
        String email,
        String site,
        String imagemLogo,
        LocalDate dataAbertura
) {
    /**
     * Constrói um DTO a partir de uma entidade Estabelecimento.
     *
     * @param est Instância da entidade Estabelecimento
     * @return EstabelecimentoResponseDto com os dados formatados
     */
    public static EstabelecimentoResponseDto toDto(Estabelecimento est) {
        return new EstabelecimentoResponseDto(
                est.getIdEstabelecimento(),
                est.getNomeFantasia(),
                est.getTelefone(),
                est.getEmail(),
                est.getSite(),
                est.getImagemLogo(),
                est.getDataAbertura()
        );
    }
}