package com.leonardoleie.teste_agrotis.controllers;

import com.leonardoleie.teste_agrotis.dtos.reponses.PessoaResponseDTO;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import com.leonardoleie.teste_agrotis.services.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.ok(pessoaService.findAll().stream().map(PessoaResponseDTO::toDto).toList());
    }

    @GetMapping("pageable")
    public ResponseEntity<Page<PessoaResponseDTO>> getAllPessoasPageable(Pageable pageable) {
        return ResponseEntity.ok(pessoaService.findAllPageable(pageable).map(PessoaResponseDTO::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id) {
        return ResponseEntity.ok(PessoaResponseDTO.toDto(pessoaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> createPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(PessoaResponseDTO.toDto(pessoaService.save(pessoa)));
    }

    @PutMapping("{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoaService.findById(id);
        pessoa.setId(id);
        return ResponseEntity.ok(PessoaResponseDTO.toDto(pessoaService.save(pessoa)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
