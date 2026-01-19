package com.example.backend.dto;

import java.math.BigDecimal;

public class BeneficioResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Boolean ativo;

    public BeneficioResponseDTO(
            Long id, String nome, String descricao, BigDecimal valor, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public Boolean getAtivo() { return ativo; }
}

