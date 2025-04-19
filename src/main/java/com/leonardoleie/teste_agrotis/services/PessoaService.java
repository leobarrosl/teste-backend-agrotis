package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import com.leonardoleie.teste_agrotis.repositories.PessoaRepository;
import com.leonardoleie.teste_agrotis.specifications.PessoaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }

    public List<Pessoa> findAll(PessoaSpecification pessoaSpecification) {
        return pessoaRepository.findAll(pessoaSpecification);
    }

    public Page<Pessoa> findAllPageable(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Pessoa save(Pessoa pessoa) {
        if (!pessoa.isValid()) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(Long id) {
        pessoaRepository.delete(findById(id));
    }
}
