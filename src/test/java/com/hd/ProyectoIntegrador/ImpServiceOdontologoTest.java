package com.hd.ProyectoIntegrador;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.repository.IOdontologoRepository;
import com.hd.ProyectoIntegrador.service.implementation.ImpServiceOdontologo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImpServiceOdontologoTest {

    @Mock
    private IOdontologoRepository iOdontologoRepository;

    @InjectMocks
    private ImpServiceOdontologo impServiceOdontologo;

    private Odontologo odontologo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        odontologo = new Odontologo(1L, "ODO123", "Juan", "Pérez", null);
    }

    @Test
    void guardar() {
        when(iOdontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        Odontologo resultado = impServiceOdontologo.guardar(odontologo);

        System.out.println("Test Guardar: " + resultado);

        assertNotNull(resultado);
        assertEquals(odontologo.getMatricula(), resultado.getMatricula());
        verify(iOdontologoRepository).save(odontologo);
    }

    @Test
    void buscarPorId() {
        Long id = 1L;
        when(iOdontologoRepository.findById(id)).thenReturn(Optional.of(odontologo));

        Odontologo resultado = impServiceOdontologo.buscarPorId(id);

        System.out.println("Test BuscarPorId: " + resultado);

        assertNotNull(resultado);
        assertEquals(odontologo, resultado);
        verify(iOdontologoRepository).findById(id);
    }

    @Test
    void eliminar() {
        Long id = 1L;
        when(iOdontologoRepository.existsById(id)).thenReturn(true);

        boolean resultado = impServiceOdontologo.eliminar(id);

        System.out.println("Test Eliminar: Resultado = " + resultado);

        assertTrue(resultado);
        verify(iOdontologoRepository).deleteById(id);
    }

    @Test
    void actualizar() {
        when(iOdontologoRepository.existsById(odontologo.getId())).thenReturn(true);
        when(iOdontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        Odontologo resultado = impServiceOdontologo.actualizar(odontologo);

        System.out.println("Test Actualizar: " + resultado);

        assertNotNull(resultado);
        assertEquals(odontologo, resultado);
        verify(iOdontologoRepository).save(odontologo);
    }

    @Test
    void listarTodos() {
        List<Odontologo> odontologos = Arrays.asList(
                new Odontologo(1L, "ODO123", "Juan", "Pérez", null),
                new Odontologo(2L, "ODO456", "María", "González", null)
        );
        when(iOdontologoRepository.findAll()).thenReturn(odontologos);

        List<Odontologo> resultado = impServiceOdontologo.listarTodos();

        System.out.println("Test ListarTodos: " + resultado);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(iOdontologoRepository).findAll();
    }

    @Test
    void listarTodos_sinOdontologos() {
        when(iOdontologoRepository.findAll()).thenReturn(List.of());

        System.out.println("Test ListarTodos_sinOdontologos: No se encontraron odontólogos.");

        assertThrows(EntityNotFoundException.class, () -> impServiceOdontologo.listarTodos());
        verify(iOdontologoRepository).findAll();
    }

    @Test
    void buscarPorMatricula() {
        String matricula = "ODO123";
        when(iOdontologoRepository.findByMatricula(matricula)).thenReturn(odontologo);

        Odontologo resultado = impServiceOdontologo.buscarPorMatricula(matricula);

        System.out.println("Test BuscarPorMatricula: " + resultado);

        assertNotNull(resultado);
        assertEquals(odontologo, resultado);
        verify(iOdontologoRepository).findByMatricula(matricula);
    }
}