package com.leonardoleie.teste_agrotis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "laboratorios")
@NoArgsConstructor
@AllArgsConstructor
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "laboratorio")
    private List<Pessoa> pessoas = new ArrayList<>();

    public boolean isValid() {
        return this.nome != null && !this.nome.isBlank();
    }

    public LocalDateTime getDataMaisAntiga() {
        return pessoas.stream()
                .map(Pessoa::getDataInicial)
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }
}
