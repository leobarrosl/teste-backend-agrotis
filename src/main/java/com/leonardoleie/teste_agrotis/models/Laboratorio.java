package com.leonardoleie.teste_agrotis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Pessoa> pessoas;

    public boolean isValid() {
        return this.nome != null && !this.nome.isBlank();
    }
}
