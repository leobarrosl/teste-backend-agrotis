package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Propriedade;
import com.leonardoleie.teste_agrotis.repositories.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public Propriedade findById(Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Propriedade não encontrada."));
    }

    public List<Propriedade> findAll() {
        return propriedadeRepository.findAll();
    }

    public Page<Propriedade> findAllPageable(Pageable pageable) {
        return propriedadeRepository.findAll(pageable);
    }

    public Propriedade save(Propriedade propriedade) {
        if (!propriedade.isValid()) {
            throw new IllegalArgumentException("Verifique os campos preenchidos novamente.");
        }
        return propriedadeRepository.save(propriedade);
    }

    public void deleteById(Long id) {
        propriedadeRepository.delete(findById(id));
    }
}
