package com.example.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferenciaRequestDTO {

    @NotNull
    private Long fromId;

    @NotNull
    private Long toId;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal valor;

    public @NotNull Long getFromId() {
        return fromId;
    }

    public void setFromId(@NotNull Long fromId) {
        this.fromId = fromId;
    }

    public @NotNull Long getToId() {
        return toId;
    }

    public void setToId(@NotNull Long toId) {
        this.toId = toId;
    }

    public @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal valor) {
        this.valor = valor;
    }
}
