package com.example.backend.mapper;

import com.example.backend.dto.BeneficioResponseDTO;
import com.example.ejb.entity.Beneficio;

public class BeneficioMapper {

    public static BeneficioResponseDTO toDTO(Beneficio b) {
        return new BeneficioResponseDTO(
                b.getId(),
                b.getNome(),
                b.getDescricao(),
                b.getValor(),
                b.getAtivo()
        );
    }
}
