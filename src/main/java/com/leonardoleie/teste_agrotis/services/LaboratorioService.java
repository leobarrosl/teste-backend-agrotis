package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.dtos.reponses.LaboratorioFormattedResponseDTO;
import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.repositories.LaboratorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public Laboratorio findById(Long id) {
        return laboratorioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Laboratório não encontrado"));
    }

    public List<Laboratorio> findAll() {
        return laboratorioRepository.findAll();
    }

    public Page<Laboratorio> findAllPageable(Pageable pageable) {
        return laboratorioRepository.findAll(pageable);
    }

    public List<Laboratorio> findAllWithFilters(Specification<Laboratorio> specification, Boolean ordenarPorEntradaMaisAntiga) {
        return ordenarLaboratorios(laboratorioRepository.findAll(specification), ordenarPorEntradaMaisAntiga);
    }

    public Laboratorio save(Laboratorio laboratorio) {
        if (!laboratorio.isValid()) {
            throw new IllegalArgumentException("Laboratório inválido");
        }
        return laboratorioRepository.save(laboratorio);
    }

    public void deleteById(Long id) {
        laboratorioRepository.delete(findById(id));
    }

    public List<Laboratorio> ordenarLaboratorios(List<Laboratorio> laboratorios, Boolean ordenarPorEntradaMaisAntiga) {
        Comparator<Laboratorio> comparator =
                Comparator.comparingInt((Laboratorio lab) -> lab.getPessoas().size())
                        .reversed();

        if (ordenarPorEntradaMaisAntiga != null && ordenarPorEntradaMaisAntiga) {
            comparator = comparator.thenComparing(Laboratorio::getDataMaisAntiga);
        }

        List<Laboratorio> resultado = laboratorios
                .stream()
                .sorted(comparator)
                .toList();

        return resultado;

    }
}
