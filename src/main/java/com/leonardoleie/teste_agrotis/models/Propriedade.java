package com.leonardoleie.teste_agrotis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "propriedades")
@NoArgsConstructor
@AllArgsConstructor
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "infosPropriedade")
    private List<Pessoa> pessoas = new ArrayList<>();

    public boolean isValid() {
        return this.nome != null && !this.nome.isBlank();
    }
}
