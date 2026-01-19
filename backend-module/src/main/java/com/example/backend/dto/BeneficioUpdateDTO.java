package com.example.backend.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class BeneficioUpdateDTO {

    private String nome;
    private String descricao;

    @Positive
    private BigDecimal valor;

    private Boolean ativo;

    public BeneficioUpdateDTO() {
    }

    public BeneficioUpdateDTO(String nome, String descricao, BigDecimal valor, Boolean ativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public @Positive BigDecimal getValor() {
        return valor;
    }

    public void setValor(@Positive BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
