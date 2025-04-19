package com.leonardoleie.teste_agrotis.dtos.reponses;

import com.leonardoleie.teste_agrotis.models.Laboratorio;

public record LaboratorioResponseDTO(Long id, String nome, Integer quantidadePessoas) {

    public static LaboratorioResponseDTO toDto(Laboratorio entity) {
        return new LaboratorioResponseDTO(
                entity.getId(),
                entity.getNome().toUpperCase(),
                entity.getPessoas() != null ? entity.getPessoas().size() : 0
        );
    }
}
