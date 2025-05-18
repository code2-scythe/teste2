package com.example.gestao_de_estoque.models;

import com.example.gestao_de_estoque.enums.Status;
import com.example.gestao_de_estoque.validations.CNPJ;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

/**
 * Entidade que representa um Estabelecimento no sistema.
 */
@Entity
@Table(name = "estabelecimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estabelecimento {

    /**
     * ID único do estabelecimento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estabelecimento", nullable = false)
    private Integer idEstabelecimento;

    /**
     * Nome fantasia do estabelecimento.
     */
    @NotBlank(message = "O campo 'Nome Fantasia' é obrigatório.")
    @Column(name = "nome_fantasia", nullable = false, length = 150)
    private String nomeFantasia;

    /**
     * Razão social do estabelecimento.
     */
    @Column(name = "razao_social", length = 200)
    private String razaoSocial;

    /**
     * CNPJ do estabelecimento.
     */
    @CNPJ(message = "Informe um CNPJ válido.")
    @Pattern(
            regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
            message = "CNPJ deve estar no formato 00.000.000/0000-00."
    )
    @Column(name = "cnpj", length = 18, unique = true)
    private String cnpj;

    /**
     * Inscrição estadual do estabelecimento.
     */
    @Column(name = "inscricao_estadual", length = 30)
    private String inscricaoEstadual;

    /**
     * Telefone do estabelecimento.
     */
    @NotBlank(message = "O campo 'Telefone' é obrigatório.")
    @Pattern(
            regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$",
            message = "Telefone deve estar no formato (99) 9999-9999 ou (99) 99999-9999."
    )
    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;

    /**
     * E-mail do estabelecimento.
     */
    @NotBlank(message = "O campo 'E-mail' é obrigatório.")
    @Email(message = "Informe um endereço de e-mail válido.")
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    /**
     * URL do site do estabelecimento.
     */
    @URL(message = "Informe uma URL válida para o site.")
    @Column(name = "site", length = 255)
    private String site;

    /**
     * URL da imagem do logotipo do estabelecimento.
     */
    @URL(message = "Informe uma URL válida para a imagem do logotipo.")
    @Column(name = "imagem_logo", length = 255)
    private String imagemLogo;

    /**
     * Data de abertura do estabelecimento.
     */
    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    /**
     * Status atual do estabelecimento.
     */
    @NotNull(message = "O campo 'Status' é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ATIVO;

    /**
     * Nome do responsável pelo estabelecimento.
     */
    @NotBlank(message = "O campo 'Responsável' é obrigatório.")
    @Column(name = "responsavel", nullable = false, length = 150)
    private String responsavel;

    /**
     * Senha de acesso do estabelecimento.
     */
    @NotBlank(message = "O campo 'Senha' é obrigatório.")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
    @Column(name = "senha", nullable = false, length = 64)
    private String senha;
}