package com.example.backend.integration;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/seed.sql")
class BeneficioEjbServiceIT {

    @PersistenceContext
    private EntityManager em;

    private BeneficioEjbService service;

    @BeforeEach
    void setup() {
        service = new BeneficioEjbService();
        service.setEntityManager(em);
    }

    // ================= SUCESSO =================

    @Test
    void deveTransferirValoresCorretamente() {

        Beneficio origem = em.find(Beneficio.class, 1L);
        Beneficio destino = em.find(Beneficio.class, 2L);

        assertEquals(new BigDecimal("1000.00"), origem.getValor());
        assertEquals(new BigDecimal("500.00"), destino.getValor());

        service.transfer(1L, 2L, new BigDecimal("200.00"));

        em.flush();
        em.clear();

        origem = em.find(Beneficio.class, 1L);
        destino = em.find(Beneficio.class, 2L);

        assertEquals(new BigDecimal("800.00"), origem.getValor());
        assertEquals(new BigDecimal("700.00"), destino.getValor());
    }


    @Test
    void deveFalharQuandoSaldoInsuficiente() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 2L, new BigDecimal("2000.00"))
        );
    }

    @Test
    void deveFalharQuandoValorInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 2L, BigDecimal.ZERO)
        );
    }

    @Test
    void deveFalharQuandoBeneficioNaoExiste() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(99L, 2L, BigDecimal.TEN)
        );
    }
}
