package com.leonardoleie.teste_agrotis.services;

import com.leonardoleie.teste_agrotis.exceptions.NotFoundException;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import com.leonardoleie.teste_agrotis.repositories.PessoaRepository;
import com.leonardoleie.teste_agrotis.specifications.PessoaSpecification;
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
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private Pessoa pessoa;

    @Mock
    private PessoaSpecification pessoaSpecification;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    void findById_DeveRetornarPessoa_QuandoExistir() {
        when(pessoa.getId()).thenReturn(1L);
        when(pessoa.getNome()).thenReturn("João");
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Pessoa resultado = pessoaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João", resultado.getNome());
    }

    @Test
    void findById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.findById(1L));
    }

    @Test
    void findAll_DeveRetornarListaDePessoas() {
        when(pessoaRepository.findAll(pessoaSpecification)).thenReturn(List.of(pessoa));

        List<Pessoa> resultado = pessoaService.findAll(pessoaSpecification);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(pessoaRepository).findAll(pessoaSpecification);
    }

    @Test
    void findAll_DeveRetornarListaVazia_QuandoNaoEncontrarResultados() {
        when(pessoaRepository.findAll(pessoaSpecification)).thenReturn(List.of());

        List<Pessoa> resultado = pessoaService.findAll(pessoaSpecification);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pessoaRepository).findAll(pessoaSpecification);
    }

    @Test
    void findAllPageable_DeveRetornarPaginaDePessoas() {
        Pageable pageable = mock(Pageable.class);
        Page<Pessoa> paginaMock = new PageImpl<>(List.of(pessoa));
        when(pessoaRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<Pessoa> resultado = pessoaService.findAllPageable(pageable);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.getTotalElements());
        verify(pessoaRepository).findAll(pageable);
    }

    @Test
    void save_DeveRetornarPessoaSalva_QuandoDadosValidos() {
        when(pessoa.isValid()).thenReturn(true);
        when(pessoa.getId()).thenReturn(1L);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa resultado = pessoaService.save(pessoa);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @Test
    void save_DeveLancarIllegalArgumentException_QuandoDadosInvalidos() {
        when(pessoa.isValid()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> pessoaService.save(pessoa));
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    void deleteById_DeveDeletarPessoa_QuandoExistir() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).delete(any(Pessoa.class));

        pessoaService.deleteById(1L);

        verify(pessoaRepository).delete(any(Pessoa.class));
    }

    @Test
    void deleteById_DeveLancarNotFoundException_QuandoNaoExistir() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.deleteById(1L));
        verify(pessoaRepository, never()).delete(any(Pessoa.class));
    }
}