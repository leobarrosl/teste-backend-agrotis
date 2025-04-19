package com.leonardoleie.teste_agrotis.dtos.reponses;

import com.leonardoleie.teste_agrotis.models.Pessoa;

import java.time.LocalDateTime;

public record PessoaResponseDTO(Long id, String nome, LocalDateTime dataInicial, LocalDateTime dataFinal,
                                String observacoes, LaboratorioResponseDTO laboratorio, PropriedadeResponseDTO propriedade) {

    public static PessoaResponseDTO toDto(Pessoa entity) {
        return new PessoaResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataInicial(),
                entity.getDataFinal(),
                entity.getObservacoes(),
                LaboratorioResponseDTO.toDto(entity.getLaboratorio()),
                PropriedadeResponseDTO.toDto(entity.getInfosPropriedade())
        );
    }
}
