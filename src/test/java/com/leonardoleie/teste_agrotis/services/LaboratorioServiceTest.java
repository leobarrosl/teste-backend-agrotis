package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.repositories.LaboratorioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratorioServiceTest {

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @Mock
    private Laboratorio laboratorio;

    @InjectMocks
    private LaboratorioService laboratorioService;

    @Test
    void findById_DeveRetornarLaboratorio_QuandoExistir() {
        when(laboratorio.getId()).thenReturn(1L);
        when(laboratorio.getNome()).thenReturn("Lab Teste");
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));

        Laboratorio resultado = laboratorioService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lab Teste", resultado.getNome());
    }

    @Test
    void findById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> laboratorioService.findById(1L));
    }

    @Test
    void findAll_DeveRetornarListaDeLaboratorios() {
        when(laboratorioRepository.findAll()).thenReturn(List.of(laboratorio));

        List<Laboratorio> resultado = laboratorioService.findAll();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void findAllPageable_DeveRetornarPaginaDeLaboratorios() {
        Pageable pageable = mock(Pageable.class);
        Page<Laboratorio> paginaMock = new PageImpl<>(List.of(laboratorio));
        when(laboratorioRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<Laboratorio> resultado = laboratorioService.findAllPageable(pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        verify(laboratorioRepository).findAll(pageable);
    }

    @Test
    void save_DeveRetornarLaboratorioSalvo_QuandoDadosValidos() {
        when(laboratorio.isValid()).thenReturn(true);
        when(laboratorio.getId()).thenReturn(1L);
        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(laboratorio);

        Laboratorio resultado = laboratorioService.save(laboratorio);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(laboratorioRepository).save(any(Laboratorio.class));
    }

    @Test
    void save_DeveLancarIllegalArgumentException_QuandoDadosInvalidos() {
        when(laboratorio.isValid()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> laboratorioService.save(laboratorio));
        verify(laboratorioRepository, never()).save(any(Laboratorio.class));
    }

    @Test
    void deleteById_DeveDeletarLaboratorio_QuandoExistir() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        doNothing().when(laboratorioRepository).delete(any(Laboratorio.class));

        laboratorioService.deleteById(1L);

        verify(laboratorioRepository).delete(any(Laboratorio.class));
    }

    @Test
    void deleteById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> laboratorioService.deleteById(1L));
        verify(laboratorioRepository, never()).delete(any(Laboratorio.class));
    }
}