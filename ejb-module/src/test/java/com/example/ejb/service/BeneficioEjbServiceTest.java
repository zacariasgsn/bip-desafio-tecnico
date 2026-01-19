package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BeneficioEjbServiceTest {

    @Mock
    private EntityManager entityManager;

    private BeneficioEjbService service;

    private Beneficio beneficioOrigem;
    private Beneficio beneficioDestino;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        service = new BeneficioEjbService();
        service.setEntityManager(entityManager);

        beneficioOrigem = new Beneficio();
        beneficioOrigem.setId(1L);
        beneficioOrigem.setValor(BigDecimal.valueOf(100));

        beneficioDestino = new Beneficio();
        beneficioDestino.setId(2L);
        beneficioDestino.setValor(BigDecimal.valueOf(50));
    }

    @Test
    void deveTransferirComSucesso() {

        when(entityManager.find(Beneficio.class, 1L))
                .thenReturn(beneficioOrigem);
        when(entityManager.find(Beneficio.class, 2L))
                .thenReturn(beneficioDestino);

        service.transfer(1L, 2L, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(70), beneficioOrigem.getValor());
        assertEquals(BigDecimal.valueOf(80), beneficioDestino.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoBeneficioNaoExiste() {

        when(entityManager.find(Beneficio.class, 1L))
                .thenReturn(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 2L, BigDecimal.TEN)
        );
    }

    @Test
    void deveLancarExcecaoQuandoValorInvalido() {

        when(entityManager.find(Beneficio.class, 1L))
                .thenReturn(beneficioOrigem);
        when(entityManager.find(Beneficio.class, 2L))
                .thenReturn(beneficioDestino);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 2L, BigDecimal.ZERO)
        );
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficiente() {

        when(entityManager.find(Beneficio.class, 1L))
                .thenReturn(beneficioOrigem);
        when(entityManager.find(Beneficio.class, 2L))
                .thenReturn(beneficioDestino);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(1L, 2L, BigDecimal.valueOf(1000))
        );
    }
}
