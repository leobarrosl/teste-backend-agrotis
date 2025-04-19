package com.leonardoleie.teste_agrotis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pessoas")
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade infosPropriedade;

    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    public boolean isValid() {
        return this.nome != null && !this.nome.isBlank()
                && this.dataInicial != null && this.dataFinal != null
                && this.infosPropriedade != null && this.laboratorio != null;
    }
}
