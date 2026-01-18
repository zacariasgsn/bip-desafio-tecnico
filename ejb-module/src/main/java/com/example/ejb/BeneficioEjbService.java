package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        // 1️⃣ Validações básicas
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs de origem e destino são obrigatórios");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Benefício de origem e destino devem ser diferentes");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }

        // 2️⃣ Lock pessimista para evitar concorrência
        Beneficio from = em.find(
                Beneficio.class,
                fromId,
                LockModeType.PESSIMISTIC_WRITE
        );

        Beneficio to = em.find(
                Beneficio.class,
                toId,
                LockModeType.PESSIMISTIC_WRITE
        );

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício de origem ou destino não encontrado");
        }

        // 3️⃣ Regra de negócio: saldo suficiente
        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência");
        }

        // 4️⃣ Atualização dos saldos
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        // 5️⃣ merge não é obrigatório (entidades já gerenciadas),
        // mas manter deixa o código mais explícito
        em.merge(from);
        em.merge(to);
    }
}
