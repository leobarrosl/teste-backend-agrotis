package com.leonardoleie.teste_agrotis.controllers;

import com.leonardoleie.teste_agrotis.dtos.reponses.PropriedadeResponseDTO;
import com.leonardoleie.teste_agrotis.models.Propriedade;
import com.leonardoleie.teste_agrotis.services.PropriedadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("propriedades")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    @GetMapping
    public ResponseEntity<List<PropriedadeResponseDTO>> getAllPropriedades() {
        return ResponseEntity.ok(propriedadeService.findAll().stream().map(PropriedadeResponseDTO::toDto).toList());
    }

    @GetMapping("pageable")
    public ResponseEntity<Page<PropriedadeResponseDTO>> getAllPropriedadesPageable(Pageable pageable) {
        return ResponseEntity.ok(propriedadeService.findAllPageable(pageable).map(PropriedadeResponseDTO::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<PropriedadeResponseDTO> getPropriedadeById(@PathVariable Long id) {
        return ResponseEntity.ok(PropriedadeResponseDTO.toDto(propriedadeService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PropriedadeResponseDTO> createPropriedade(@RequestBody Propriedade propriedade) {
        return ResponseEntity.ok(PropriedadeResponseDTO.toDto(propriedadeService.save(propriedade)));
    }

    @PutMapping("{id}")
    public ResponseEntity<PropriedadeResponseDTO> updatePropriedade(@PathVariable Long id, @RequestBody Propriedade propriedade) {
        propriedadeService.findById(id);
        propriedade.setId(id);
        return ResponseEntity.ok(PropriedadeResponseDTO.toDto(propriedadeService.save(propriedade)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        propriedadeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
