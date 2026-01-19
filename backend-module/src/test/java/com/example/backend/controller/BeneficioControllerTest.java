package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.service.BeneficioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioService beneficioService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveListarBeneficios() throws Exception {

        BeneficioResponseDTO dto = new BeneficioResponseDTO(
                1L, "Benefício A", "Desc", BigDecimal.valueOf(100), true
        );

        when(beneficioService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Benefício A"));
    }

    @Test
    void deveBuscarBeneficioPorId() throws Exception {

        BeneficioResponseDTO dto = new BeneficioResponseDTO(
                1L, "Benefício A", "Desc", BigDecimal.valueOf(100), true
        );

        when(beneficioService.buscarPorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/beneficios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Benefício A"));
    }

    @Test
    void deveCriarBeneficio() throws Exception {

        BeneficioCreateDTO request = new BeneficioCreateDTO(
                "Novo", "Desc", BigDecimal.valueOf(200)
        );

        BeneficioResponseDTO response = new BeneficioResponseDTO(
                1L, "Novo", "Desc", BigDecimal.valueOf(200), true
        );

        when(beneficioService.criar(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveAtualizarBeneficio() throws Exception {

        BeneficioUpdateDTO request = new BeneficioUpdateDTO(
                "Atualizado", null, BigDecimal.valueOf(300), true
        );

        BeneficioResponseDTO response = new BeneficioResponseDTO(
                1L, "Atualizado", "Desc", BigDecimal.valueOf(300), true
        );

        when(beneficioService.atualizar(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/beneficios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Atualizado"));
    }


    @Test
    void deveExcluirBeneficio() throws Exception {

        doNothing().when(beneficioService).excluir(1L);

        mockMvc.perform(delete("/api/v1/beneficios/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    void deveTransferirComSucesso() throws Exception {

        TransferenciaRequestDTO dto = new TransferenciaRequestDTO();
        dto.setFromId(1L);
        dto.setToId(2L);
        dto.setValor(BigDecimal.valueOf(10));

        doNothing().when(beneficioService)
                .transferir(1L, 2L, dto.getValor());

        mockMvc.perform(post("/api/v1/beneficios/transferir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarBadRequestQuandoValorInvalido() throws Exception {

        TransferenciaRequestDTO dto = new TransferenciaRequestDTO();
        dto.setFromId(1L);
        dto.setToId(2L);
        dto.setValor(BigDecimal.ZERO);

        doThrow(new IllegalArgumentException("Valor inválido"))
                .when(beneficioService)
                .transferir(1L, 2L, dto.getValor());

        mockMvc.perform(post("/api/v1/beneficios/transferir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
