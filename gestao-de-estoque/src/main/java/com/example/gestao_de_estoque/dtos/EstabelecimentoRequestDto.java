package com.example.gestao_de_estoque.dtos;

import com.example.gestao_de_estoque.models.Estabelecimento;
import com.example.gestao_de_estoque.enums.Status;
import java.time.LocalDate;

/**
 * DTO utilizado para receber os dados de criação ou atualização de um Estabelecimento.
 */
public record EstabelecimentoRequestDto(
        String nomeFantasia,
        String razaoSocial,
        String cnpj,
        String inscricaoEstadual,
        String telefone,
        String email,
        String site,
        String imagemLogo,
        LocalDate dataAbertura,
        Status status,
        String responsavel,
        String senha
) {
    /**
     * Copia os dados deste DTO para a entidade Estabelecimento.
     *
     * @param estabelecimento Instância da entidade a ser atualizada
     * @return Estabelecimento com os dados preenchidos a partir do DTO
     */
    public Estabelecimento toEstabelecimento(Estabelecimento estabelecimento) {
        estabelecimento.setNomeFantasia(nomeFantasia());
        estabelecimento.setRazaoSocial(razaoSocial());
        estabelecimento.setCnpj(cnpj());
        estabelecimento.setInscricaoEstadual(inscricaoEstadual());
        estabelecimento.setTelefone(telefone());
        estabelecimento.setEmail(email());
        estabelecimento.setSite(site());
        estabelecimento.setImagemLogo(imagemLogo());
        estabelecimento.setDataAbertura(dataAbertura());
        estabelecimento.setStatus(status());
        estabelecimento.setResponsavel(responsavel());
        estabelecimento.setSenha(senha());
        return estabelecimento;
    }
}