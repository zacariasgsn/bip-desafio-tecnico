package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;


public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Beneficio from = em.find(Beneficio.class, fromId);
        Beneficio to   = em.find(Beneficio.class, toId);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
