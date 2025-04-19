package com.leonardoleie.teste_agrotis.dtos.reponses;

import com.leonardoleie.teste_agrotis.models.Propriedade;

public record PropriedadeResponseDTO(Long id, String nome,
                                     Integer quantidadePessoas) {

    public static PropriedadeResponseDTO toDto(Propriedade entity) {
        return new PropriedadeResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getPessoas() != null ? entity.getPessoas().size() : 0
        );
    }
}
