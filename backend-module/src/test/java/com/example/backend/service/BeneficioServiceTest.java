package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.entity.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioServiceTest {

    @Mock
    private BeneficioRepository repository;

    @Mock
    private BeneficioEjbService ejbService;

    @InjectMocks
    private BeneficioService service;


    @Test
    void deveListarBeneficios() {
        Beneficio b = new Beneficio();
        b.setId(1L);
        b.setNome("Teste");
        b.setValor(BigDecimal.TEN);
        b.setAtivo(true);

        when(repository.findAll()).thenReturn(List.of(b));

        List<BeneficioResponseDTO> result = service.listar();

        assertEquals(1, result.size());
        assertEquals("Teste", result.get(0).getNome());
    }


    @Test
    void deveBuscarPorId() {
        Beneficio b = new Beneficio();
        b.setId(1L);
        b.setNome("Benefício");
        b.setValor(BigDecimal.TEN);
        b.setAtivo(true);

        when(repository.findById(1L)).thenReturn(Optional.of(b));

        BeneficioResponseDTO dto = service.buscarPorId(1L);

        assertEquals("Benefício", dto.getNome());
    }

    @Test
    void deveLancarErroQuandoBuscarIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.buscarPorId(1L)
        );
    }

    @Test
    void deveCriarBeneficio() {
        Beneficio salvo = new Beneficio();
        salvo.setId(1L);
        salvo.setNome("Novo");
        salvo.setValor(BigDecimal.valueOf(100));
        salvo.setAtivo(true);

        when(repository.save(any())).thenReturn(salvo);

        BeneficioCreateDTO dto = new BeneficioCreateDTO(
                "Novo", "Desc", BigDecimal.valueOf(100)
        );

        BeneficioResponseDTO result = service.criar(dto);

        assertEquals(1L, result.getId());
        assertEquals("Novo", result.getNome());
    }

    @Test
    void deveAtualizarBeneficio() {
        Beneficio existente = new Beneficio();
        existente.setId(1L);
        existente.setNome("Antigo");
        existente.setValor(BigDecimal.TEN);
        existente.setAtivo(true);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        BeneficioUpdateDTO dto = new BeneficioUpdateDTO(
                "Atualizado", null, BigDecimal.valueOf(200), false
        );

        BeneficioResponseDTO result = service.atualizar(1L, dto);

        assertEquals("Atualizado", result.getNome());
        assertFalse(result.getAtivo());
    }

    @Test
    void deveExcluirBeneficio() {
        when(repository.existsById(1L)).thenReturn(true);

        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deveLancarErroAoExcluirInexistente() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.excluir(1L)
        );
    }

    @Test
    void deveDelegarTransferenciaComSucesso() {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal valor = BigDecimal.valueOf(100);

        service.transferir(fromId, toId, valor);

        verify(ejbService).transfer(fromId, toId, valor);
    }

    @Test
    void devePropagarErroQuandoValorInvalido() {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal valor = BigDecimal.ZERO;

        doThrow(new IllegalArgumentException("Valor inválido"))
                .when(ejbService)
                .transfer(fromId, toId, valor);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transferir(fromId, toId, valor)
        );

        verify(ejbService).transfer(fromId, toId, valor);
    }

    @Test
    void devePropagarErroQuandoSaldoInsuficiente() {

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal valor = BigDecimal.valueOf(10_000);

        doThrow(new IllegalArgumentException("Saldo insuficiente"))
                .when(ejbService)
                .transfer(fromId, toId, valor);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transferir(fromId, toId, valor)
        );

        verify(ejbService).transfer(fromId, toId, valor);
    }

    @Test
    void devePropagarErroQuandoBeneficioNaoEncontrado() {

        Long fromId = 99L;
        Long toId = 100L;
        BigDecimal valor = BigDecimal.TEN;

        doThrow(new IllegalArgumentException("Benefício não encontrado"))
                .when(ejbService)
                .transfer(fromId, toId, valor);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.transferir(fromId, toId, valor)
        );

        verify(ejbService).transfer(fromId, toId, valor);
    }
}
