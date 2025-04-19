package com.leonardoleie.teste_agrotis.repositories;

import com.leonardoleie.teste_agrotis.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
