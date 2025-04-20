package com.leonardoleie.teste_agrotis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import com.leonardoleie.teste_agrotis.models.Propriedade;
import com.leonardoleie.teste_agrotis.repositories.LaboratorioRepository;
import com.leonardoleie.teste_agrotis.repositories.PessoaRepository;
import com.leonardoleie.teste_agrotis.repositories.PropriedadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    private Pessoa pessoa;
    private Propriedade propriedade;
    private Laboratorio laboratorio;

    @BeforeEach
    void setUp() {
        propriedade = new Propriedade();
        propriedade.setNome("Propriedade Teste");
        propriedadeRepository.save(propriedade);

        laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório Teste");
        laboratorioRepository.save(laboratorio);

        pessoa = new Pessoa();
        pessoa.setNome("Pessoa Teste");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoa.setInfosPropriedade(propriedade);
        pessoa.setLaboratorio(laboratorio);
        pessoa.setObservacoes("Observação teste");
        pessoaRepository.save(pessoa);
    }

    @Test
    void shouldReturnAllPessoas() throws Exception {
        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Pessoa Teste"));
    }

    @Test
    void shouldFindPessoaById() throws Exception {
        mockMvc.perform(get("/pessoas/" + pessoa.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pessoa Teste"));
    }

    @Test
    void shouldReturnPagePessoa() throws Exception {
        mockMvc.perform(get("/pessoas/pageable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void shouldCreatePessoa() throws Exception {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome("Nova Pessoa");
        novaPessoa.setDataInicial(LocalDateTime.now());
        novaPessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        novaPessoa.setInfosPropriedade(propriedade);
        novaPessoa.setLaboratorio(laboratorio);
        novaPessoa.setObservacoes("Nova observação");

        mockMvc.perform(post("/pessoas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(novaPessoa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nova Pessoa"));
    }

    @Test
    void shouldUpdatePessoa() throws Exception {
        pessoa.setNome("Pessoa Atualizada");

        mockMvc.perform(put("/pessoas/" + pessoa.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pessoa Atualizada"));
    }

    @Test
    void shouldDeletePessoa() throws Exception {
        mockMvc.perform(delete("/pessoas/" + pessoa.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pessoas/" + pessoa.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenPessoaNotFound() throws Exception {
        mockMvc.perform(get("/pessoas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenCreateInvalidPessoa() throws Exception {
        Pessoa invalidPessoa = new Pessoa();
        invalidPessoa.setNome("");

        mockMvc.perform(post("/pessoas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidPessoa)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFilterPessoasByDates() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        mockMvc.perform(get("/pessoas")
                        .param("dataInicialMinima", now.minusDays(1).toString())
                        .param("dataInicialMaxima", now.plusDays(1).toString())
                        .param("dataFinalMinima", now.toString())
                        .param("dataFinalMaxima", now.plusDays(2).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFilterPessoasByObservacoes() throws Exception {
        mockMvc.perform(get("/pessoas")
                        .param("observacoes", "teste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/pessoas")
                        .param("observacoes", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnErrorWhenDatesAreInvalid() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        mockMvc.perform(get("/pessoas")
                        .param("dataInicialMinima", now.plusDays(1).toString())
                        .param("dataInicialMaxima", now.toString()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/pessoas")
                        .param("dataFinalMinima", now.plusDays(1).toString())
                        .param("dataFinalMaxima", now.toString()))
                .andExpect(status().isBadRequest());
    }
}