package com.leonardoleie.teste_agrotis.dtos.reponses;

import com.leonardoleie.teste_agrotis.models.Propriedade;

public record PropriedadeResponseDTO(Long id, String nome) {

    public static PropriedadeResponseDTO toDto(Propriedade entity) {
        return new PropriedadeResponseDTO(
                entity.getId(),
                entity.getNome()
        );
    }
}
