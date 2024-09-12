package com.hd.ProyectoIntegrador;

import com.hd.ProyectoIntegrador.model.Domicilio;
import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.repository.IPacienteRepository;
import com.hd.ProyectoIntegrador.service.implementation.ImpServicePaciente;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImpServicePacienteTest {

    @Mock
    private IPacienteRepository iPacienteRepository;

    @InjectMocks
    private ImpServicePaciente impServicePaciente;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Domicilio domicilio = new Domicilio(1L, "Calle Falsa", 123, "Springfield", "Springfield", paciente);
        paciente = new Paciente(1L, "John", "Doe", "12345678", LocalDate.now(), domicilio, null);

    }

    @Test
    void guardar() {
        when(iPacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = impServicePaciente.guardar(paciente);

        System.out.println("Test Guardar: " + resultado);

        assertNotNull(resultado);
        assertEquals(paciente.getNombre(), resultado.getNombre());
        verify(iPacienteRepository).save(paciente);
    }

    @Test
    void buscarPorId() {
        Long id = 1L;
        when(iPacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        Paciente resultado = impServicePaciente.buscarPorId(id);

        System.out.println("Test BuscarPorId: " + resultado);

        assertNotNull(resultado);
        assertEquals(paciente, resultado);
        verify(iPacienteRepository).findById(id);
    }

    @Test
    void eliminar() {
        Long id = 1L;
        when(iPacienteRepository.existsById(id)).thenReturn(true);

        boolean resultado = impServicePaciente.eliminar(id);

        System.out.println("Test Eliminar: Resultado = " + resultado);

        assertTrue(resultado);
        verify(iPacienteRepository).deleteById(id);
    }

    @Test
    void actualizar() {
        when(iPacienteRepository.existsById(paciente.getId())).thenReturn(true);
        when(iPacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = impServicePaciente.actualizar(paciente);

        System.out.println("Test Actualizar: " + resultado);

        assertNotNull(resultado);
        assertEquals(paciente, resultado);
        verify(iPacienteRepository).save(paciente);
    }

    @Test
    void listarTodos() {
        List<Paciente> pacientes = Arrays.asList(
                new Paciente(1L, "John", "Doe", "12345678", LocalDate.now(), null, null),
                new Paciente(2L, "Jane", "Doe", "87654321", LocalDate.now(), null, null)
        );
        when(iPacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> resultado = impServicePaciente.listarTodos();

        System.out.println("Test ListarTodos: " + resultado);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(iPacienteRepository).findAll();
    }

    @Test
    void listarTodos_sinPacientes() {
        when(iPacienteRepository.findAll()).thenReturn(List.of());

        System.out.println("Test ListarTodos_sinPacientes: No se encontraron pacientes.");

        assertThrows(EntityNotFoundException.class, () -> impServicePaciente.listarTodos());
        verify(iPacienteRepository).findAll();
    }

    @Test
    void buscarPorDni() {
        String dni = "12345678";
        when(iPacienteRepository.findByDni(dni)).thenReturn(paciente);

        Paciente resultado = impServicePaciente.buscarPorDni(dni);

        System.out.println("Test BuscarPorDni: " + resultado);

        assertNotNull(resultado);
        assertEquals(paciente, resultado);
        verify(iPacienteRepository).findByDni(dni);
    }
}
