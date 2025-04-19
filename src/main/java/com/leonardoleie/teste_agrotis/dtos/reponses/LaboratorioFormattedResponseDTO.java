package com.leonardoleie.teste_agrotis.dtos.reponses;

import com.leonardoleie.teste_agrotis.models.Laboratorio;

public record LaboratorioFormattedResponseDTO(Long id, String nome, Integer quantidadePessoas) {

    public static LaboratorioFormattedResponseDTO toDto(Laboratorio entity) {
        return new LaboratorioFormattedResponseDTO(
                entity.getId(),
                entity.getNome().toUpperCase(),
                entity.getPessoas() != null ? entity.getPessoas().size() : 0
        );
    }
}
