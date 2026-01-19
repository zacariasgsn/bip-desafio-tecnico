package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.mapper.BeneficioMapper;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.entity.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository repository;
    private final BeneficioEjbService ejbService;

    public BeneficioService(
            BeneficioRepository repository,
            BeneficioEjbService ejbService
    ) {
        this.repository = repository;
        this.ejbService = ejbService;
    }

    @Transactional(readOnly = true)
    public List<BeneficioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(BeneficioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BeneficioResponseDTO buscarPorId(Long id) {
        Beneficio beneficio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Benefício não encontrado"));

        return BeneficioMapper.toDTO(beneficio);
    }

    @Transactional
    public BeneficioResponseDTO criar(BeneficioCreateDTO dto) {

        Beneficio beneficio = new Beneficio();
        beneficio.setNome(dto.getNome());
        beneficio.setDescricao(dto.getDescricao());
        beneficio.setValor(dto.getValor());
        beneficio.setAtivo(true);

        Beneficio salvo = repository.save(beneficio);
        return BeneficioMapper.toDTO(salvo);
    }

    @Transactional
    public BeneficioResponseDTO atualizar(Long id, BeneficioUpdateDTO dto) {

        Beneficio beneficio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Benefício não encontrado"));

        if (dto.getNome() != null) {
            beneficio.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            beneficio.setDescricao(dto.getDescricao());
        }
        if (dto.getValor() != null) {
            beneficio.setValor(dto.getValor());
        }
        if (dto.getAtivo() != null) {
            beneficio.setAtivo(dto.getAtivo());
        }

        return BeneficioMapper.toDTO(beneficio);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Benefício não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void transferir(Long fromId, Long toId, java.math.BigDecimal valor) {
        ejbService.transfer(fromId, toId, valor);
    }
}
