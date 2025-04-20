package com.leonardoleie.teste_agrotis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import com.leonardoleie.teste_agrotis.repositories.LaboratorioRepository;
import com.leonardoleie.teste_agrotis.repositories.PessoaRepository;
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
class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private Laboratorio laboratorio;

    @BeforeEach
    void setUp() {
        laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório Teste");
        laboratorioRepository.save(laboratorio);
    }

    @Test
    void shouldReturnAllLaboratorios() throws Exception {
        mockMvc.perform(get("/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFindLaboratorioById() throws Exception {
        mockMvc.perform(get("/laboratorios/" + laboratorio.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Laboratório Teste"));
    }

    @Test
    void shouldReturnPageLaboratorio() throws Exception {
        mockMvc.perform(get("/laboratorios/pageable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void shouldCreateLaboratorio() throws Exception {
        Laboratorio novoLaboratorio = new Laboratorio();
        novoLaboratorio.setNome("Novo Laboratório");

        mockMvc.perform(post("/laboratorios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(novoLaboratorio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Laboratório"));
    }

    @Test
    void shouldReturnErrorWhenLaboratorioIsInvalid() throws Exception {
        Laboratorio novoLaboratorio = new Laboratorio();
        novoLaboratorio.setNome("");

        mockMvc.perform(post("/laboratorios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(novoLaboratorio)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateLaboratorio() throws Exception {
        laboratorio.setNome("Laboratório Atualizado");

        mockMvc.perform(put("/laboratorios/" + laboratorio.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(laboratorio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Laboratório Atualizado"));
    }

    @Test
    void shouldDeleteLaboratorio() throws Exception {
        mockMvc.perform(delete("/laboratorios/" + laboratorio.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnErrorWhenLaboratorioNotFound() throws Exception {
        mockMvc.perform(get("/laboratorios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenCreateInvalidLaboratorio() throws Exception {
        Laboratorio invalidLaboratorio = new Laboratorio();
        invalidLaboratorio.setNome("");

        mockMvc.perform(post("/laboratorios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidLaboratorio)))
                .andExpect(status().isBadRequest());
    }

    // Testes para os filtros

    @Test
    void shouldFilterLaboratoriosByQuantidadeMinima() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoa.setLaboratorio(laboratorio);
        pessoa.setObservacoes("Observação teste");
        pessoaRepository.save(pessoa);

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldFilterLaboratoriosByDates() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa.setDataInicial(now);
        pessoa.setDataFinal(now.plusDays(1));
        pessoa.setLaboratorio(laboratorio);
        pessoaRepository.save(pessoa);

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "0")
                        .param("dataInicialMinima", now.minusDays(1).toString())
                        .param("dataInicialMaxima", now.plusDays(1).toString())
                        .param("dataFinalMinima", now.toString())
                        .param("dataFinalMaxima", now.plusDays(2).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFilterLaboratoriosByObservacoes() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Pessoa 1");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoa.setLaboratorio(laboratorio);
        pessoa.setObservacoes("Observação específica");
        pessoaRepository.save(pessoa);

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "0")
                        .param("observacoes", "específica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "0")
                        .param("observacoes", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnErrorWhenQuantidadeMinimaIsNull() throws Exception {
        mockMvc.perform(get("/laboratorios/with-filters"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorWhenDatesAreInvalid() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "1")
                        .param("dataInicialMinima", now.plusDays(1).toString())
                        .param("dataInicialMaxima", now.toString()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/laboratorios/with-filters")
                        .param("quantidadeMinima", "1")
                        .param("dataFinalMinima", now.plusDays(1).toString())
                        .param("dataFinalMaxima", now.toString()))
                .andExpect(status().isBadRequest());
    }
}