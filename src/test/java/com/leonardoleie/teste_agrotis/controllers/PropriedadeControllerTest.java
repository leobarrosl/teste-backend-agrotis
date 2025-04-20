package com.leonardoleie.teste_agrotis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.models.Propriedade;
import com.leonardoleie.teste_agrotis.repositories.PropriedadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PropriedadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    private Propriedade propriedade;

    @BeforeEach
    void setUp() {
        propriedade = new Propriedade();
        propriedade.setNome("Propriedade Teste");
        propriedadeRepository.save(propriedade);
    }

    @Test
    void shouldReturnAllPropriedades() throws Exception {
        mockMvc.perform(get("/propriedades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Propriedade Teste"));
    }

    @Test
    void shouldFindPropriedadeById() throws Exception {
        mockMvc.perform(get("/propriedades/" + propriedade.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Propriedade Teste"));
    }

    @Test
    void shouldReturnPagePropriedade() throws Exception {
        mockMvc.perform(get("/propriedades/pageable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Propriedade Teste"));
    }

    @Test
    void shouldCreatePropriedade() throws Exception {
        Propriedade novaPropriedade = new Propriedade();
        novaPropriedade.setNome("Nova Propriedade");

        mockMvc.perform(post("/propriedades")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(novaPropriedade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nova Propriedade"));
    }

    @Test
    void shouldReturnErrorWhenPropriedadeIsInvalid() throws Exception {
        Propriedade novaPropriedade = new Propriedade();
        novaPropriedade.setNome("");

        mockMvc.perform(post("/propriedades")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(novaPropriedade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdatePropriedade() throws Exception {
        propriedade.setNome("Propriedade Atualizada");

        mockMvc.perform(put("/propriedades/" + propriedade.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(propriedade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Propriedade Atualizada"));
    }

    @Test
    void shouldDeletePropriedade() throws Exception {
        mockMvc.perform(delete("/propriedades/" + propriedade.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/propriedades/" + propriedade.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenPropriedadeNotFound() throws Exception {
        mockMvc.perform(get("/propriedades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnErrorWhenCreateInvalidPropriedade() throws Exception {
        Propriedade invalidPropriedade = new Propriedade();
        invalidPropriedade.setNome("");

        mockMvc.perform(post("/propriedades")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidPropriedade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorWhenUpdateInvalidPropriedade() throws Exception {
        propriedade.setNome("");

        mockMvc.perform(put("/propriedades/" + propriedade.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(propriedade)))
                .andExpect(status().isBadRequest());
    }
}