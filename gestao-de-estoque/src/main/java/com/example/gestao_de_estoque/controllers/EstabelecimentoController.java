package com.example.gestao_de_estoque.controllers;

import com.example.gestao_de_estoque.dtos.EstabelecimentoRequestDto;
import com.example.gestao_de_estoque.dtos.EstabelecimentoResponseDto;
import com.example.gestao_de_estoque.dtos.LoginRequest;
import com.example.gestao_de_estoque.dtos.LoginResponse;
import com.example.gestao_de_estoque.enums.Status;
import com.example.gestao_de_estoque.exceptions.UnauthorizedException;
import com.example.gestao_de_estoque.models.Estabelecimento;
import com.example.gestao_de_estoque.repositories.EstabelecimentoRepository;
import com.example.gestao_de_estoque.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController // Define que esta classe é um controlador REST (API)
@RequestMapping("estabelecimentos") // Define a rota base para os endpoints: http://localhost:8080/estabelecimentos
public class EstabelecimentoController {

    // Injeção de dependências: repositório, utilitário de JWT e codificador de senha
    private final EstabelecimentoRepository repository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // Construtor com as dependências necessárias
    public EstabelecimentoController(EstabelecimentoRepository repository,
                                     JwtUtil jwtUtil,
                                     PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------------------
    // GET - Listar com paginação
    // ---------------------------
    // Exemplo de URL no Postman:
    // http://localhost:8080/estabelecimentos?numero_pagina=0&quantidade=5
    @GetMapping
    public ResponseEntity<Page<EstabelecimentoResponseDto>> findAll(
            @RequestParam(name = "numero_pagina", defaultValue = "0") int pagina,
            @RequestParam(name = "quantidade", defaultValue = "5") int qtd) {
        PageRequest pr = PageRequest.of(pagina, qtd);
        Page<EstabelecimentoResponseDto> page = repository.findAll(pr)
                .map(EstabelecimentoResponseDto::toDto);
        return ResponseEntity.ok(page);
    }

    // ---------------------------
    // GET - Buscar por ID
    // ---------------------------
    // Exemplo de URL no Postman:
    // http://localhost:8080/estabelecimentos/1
    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDto> findById(@PathVariable Integer id) {
        Estabelecimento est = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estabelecimento não encontrado."));
        return ResponseEntity.ok(EstabelecimentoResponseDto.toDto(est));
    }

    // ---------------------------
    // GET - Buscar por e-mail
    // ---------------------------
    // Exemplo de URL no Postman:
    // http://localhost:8080/estabelecimentos/email/teste@email.com
    @GetMapping("/email/{email}")
    public ResponseEntity<EstabelecimentoResponseDto> findByEmail(@PathVariable String email) {
        Estabelecimento est = repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Estabelecimento não encontrado."));
        return ResponseEntity.ok(EstabelecimentoResponseDto.toDto(est));
    }

    // ---------------------------
    // POST - Criar novo estabelecimento
    // ---------------------------
    // Exemplo de URL no Postman:
    // POST http://localhost:8080/estabelecimentos
    // Body (JSON):
    // {
    //   "nome": "Loja A",
    //   "email": "loja@email.com",
    //   "senha": "123456"
    // }
    @PostMapping
    public ResponseEntity<EstabelecimentoResponseDto> save(
            @RequestBody @Valid EstabelecimentoRequestDto dto) {
        Estabelecimento est = dto.toEstabelecimento(new Estabelecimento());
        est.setSenha(passwordEncoder.encode(dto.senha())); // Codifica a senha
        repository.save(est);
        URI uri = URI.create("/estabelecimentos/" + est.getIdEstabelecimento());
        return ResponseEntity.created(uri).body(EstabelecimentoResponseDto.toDto(est));
    }

    // ---------------------------
    // PUT - Atualizar estabelecimento por ID
    // ---------------------------
    // Exemplo de URL no Postman:
    // PUT http://localhost:8080/estabelecimentos/1
    // Body (JSON):
    // {
    //   "nome": "Loja Atualizada",
    //   "email": "nova@email.com",
    //   "senha": "novasenha"
    // }
    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDto> updateById(
            @PathVariable Integer id,
            @RequestBody @Valid EstabelecimentoRequestDto dto) {
        Estabelecimento est = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estabelecimento não encontrado."));
        dto.toEstabelecimento(est); // Atualiza dados exceto senha
        if (dto.senha() != null && !dto.senha().isBlank()) {
            est.setSenha(passwordEncoder.encode(dto.senha())); // Atualiza a senha, se enviada
        }
        repository.save(est);
        return ResponseEntity.ok(EstabelecimentoResponseDto.toDto(est));
    }

    // ---------------------------
    // DELETE - Remover estabelecimento por ID
    // ---------------------------
    // Exemplo de URL no Postman:
    // DELETE http://localhost:8080/estabelecimentos/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Estabelecimento não encontrado.");
        }
        repository.deleteById(id);
    }

    // ---------------------------
    // POST - Login e geração de JWT
    // ---------------------------
    // Exemplo de URL no Postman:
    // POST http://localhost:8080/estabelecimentos/login
    // Body (JSON):
    // {
    //   "email": "loja@email.com",
    //   "senha": "123456"
    // }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest dto) {
        Optional<Estabelecimento> opt = repository.findByEmail(dto.email());

        if (opt.isEmpty() || !passwordEncoder.matches(dto.senha(), opt.get().getSenha())) {
            throw new UnauthorizedException("Credenciais inválidas.");
        }

        Estabelecimento est = opt.get();

        if (est.getStatus() == Status.INATIVO || est.getStatus() == Status.PENDENTE) {
            throw new UnauthorizedException("Estabelecimento não autorizado para login.");
        }

        String token = jwtUtil.generateToken(est.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}