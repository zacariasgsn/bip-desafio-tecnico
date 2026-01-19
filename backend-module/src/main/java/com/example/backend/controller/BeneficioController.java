package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.service.BeneficioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/beneficios")
@Tag(name = "Benefícios", description = "Operações de Benefícios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar benefícios")
    @GetMapping
    public ResponseEntity<List<BeneficioResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar benefício por ID")
    @GetMapping("/{id}")
    public ResponseEntity<BeneficioResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Criar benefício")
    @PostMapping
    public ResponseEntity<BeneficioResponseDTO> criar(
            @RequestBody @Valid BeneficioCreateDTO dto
    ) {
        BeneficioResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar benefício")
    @PutMapping("/{id}")
    public ResponseEntity<BeneficioResponseDTO> atualizar(
            @PathVariable("id") Long id,
            @RequestBody @Valid BeneficioUpdateDTO dto
    ) {
        BeneficioResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir benefício")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Transferir valor entre benefícios")
    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(
            @RequestBody @Valid TransferenciaRequestDTO dto
    ) {
        service.transferir(dto.getFromId(), dto.getToId(), dto.getValor());
        return ResponseEntity.ok().build();
    }
}
