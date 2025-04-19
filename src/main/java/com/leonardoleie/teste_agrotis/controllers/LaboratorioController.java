package com.leonardoleie.teste_agrotis.controllers;

import com.leonardoleie.teste_agrotis.dtos.reponses.LaboratorioFormattedResponseDTO;
import com.leonardoleie.teste_agrotis.dtos.reponses.LaboratorioResponseDTO;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.services.LaboratorioService;
import com.leonardoleie.teste_agrotis.specifications.LaboratorioSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;

    @GetMapping
    public ResponseEntity<List<LaboratorioResponseDTO>> getAllLaboratorios() {
        return ResponseEntity.ok(laboratorioService.findAll().stream().map(LaboratorioResponseDTO::toDto).toList());
    }

    @GetMapping("pageable")
    public ResponseEntity<Page<LaboratorioResponseDTO>> getAllLaboratoriosPageable(Pageable pageable) {
        return ResponseEntity.ok(laboratorioService.findAllPageable(pageable).map(LaboratorioResponseDTO::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<LaboratorioResponseDTO> getLaboratorioById(@PathVariable Long id) {
        return ResponseEntity.ok(LaboratorioResponseDTO.toDto(laboratorioService.findById(id)));
    }

    @GetMapping("with-filters")
    public ResponseEntity<List<LaboratorioFormattedResponseDTO>> getAllLaboratoriosWithFilters(LaboratorioSpecification filters) {
        return ResponseEntity.ok(
                laboratorioService.findAllWithFilters(filters)
                        .stream()
                        .map(LaboratorioFormattedResponseDTO::toDto)
                        .sorted((lab1, lab2) -> Integer.compare(lab2.quantidadePessoas(), lab1.quantidadePessoas()))
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<LaboratorioResponseDTO> createLaboratorio(@RequestBody Laboratorio laboratorio) {
        return ResponseEntity.ok(LaboratorioResponseDTO.toDto(laboratorioService.save(laboratorio)));
    }

    @PutMapping("{id}")
    public ResponseEntity<LaboratorioResponseDTO> updateLaboratorio(@PathVariable Long id, @RequestBody Laboratorio laboratorio) {
        laboratorioService.findById(id);
        laboratorio.setId(id);
        return ResponseEntity.ok(LaboratorioResponseDTO.toDto(laboratorioService.save(laboratorio)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLaboratorio(@PathVariable Long id) {
        laboratorioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
