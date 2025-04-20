package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Propriedade;
import com.leonardoleie.teste_agrotis.repositories.PropriedadeRepository;
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
class PropriedadeServiceTest {

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private Propriedade propriedade;

    @InjectMocks
    private PropriedadeService propriedadeService;

    @Test
    void findById_DeveRetornarPropriedade_QuandoExistir() {
        when(propriedade.getId()).thenReturn(1L);
        when(propriedade.getNome()).thenReturn("Propriedade Teste");
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));

        Propriedade resultado = propriedadeService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Propriedade Teste", resultado.getNome());
    }

    @Test
    void findById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> propriedadeService.findById(1L));
    }

    @Test
    void findAll_DeveRetornarListaDePropriedades() {
        when(propriedadeRepository.findAll()).thenReturn(List.of(propriedade));

        List<Propriedade> resultado = propriedadeService.findAll();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void findAllPageable_DeveRetornarPaginaDePropriedades() {
        Pageable pageable = mock(Pageable.class);
        Page<Propriedade> paginaMock = new PageImpl<>(List.of(propriedade));
        when(propriedadeRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<Propriedade> resultado = propriedadeService.findAllPageable(pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        verify(propriedadeRepository).findAll(pageable);
    }

    @Test
    void save_DeveRetornarPropriedadeSalva_QuandoDadosValidos() {
        when(propriedade.isValid()).thenReturn(true);
        when(propriedade.getId()).thenReturn(1L);
        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        Propriedade resultado = propriedadeService.save(propriedade);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(propriedadeRepository).save(any(Propriedade.class));
    }

    @Test
    void save_DeveLancarIllegalArgumentException_QuandoDadosInvalidos() {
        when(propriedade.isValid()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> propriedadeService.save(propriedade));
        verify(propriedadeRepository, never()).save(any(Propriedade.class));
    }

    @Test
    void deleteById_DeveDeletarPropriedade_QuandoExistir() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        doNothing().when(propriedadeRepository).delete(any(Propriedade.class));

        propriedadeService.deleteById(1L);

        verify(propriedadeRepository).delete(any(Propriedade.class));
    }

    @Test
    void deleteById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> propriedadeService.deleteById(1L));
        verify(propriedadeRepository, never()).delete(any(Propriedade.class));
    }
}