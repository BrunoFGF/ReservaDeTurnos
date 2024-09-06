package com.hd.ProyectoIntegrador;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.model.Turno;
import com.hd.ProyectoIntegrador.repository.ITurnoRepository;
import com.hd.ProyectoIntegrador.service.implementation.ImpServiceTurno;
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

class ImpServiceTurnoTest {

    @Mock
    private ITurnoRepository iTurnoRepository;

    @InjectMocks
    private ImpServiceTurno impServiceTurno;

    private Turno turno;
    private Odontologo odontologo;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Dr. Smith");
        odontologo.setApellido("Jones");

        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");

        turno = new Turno();
        turno.setId(1L);
        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);
        turno.setFecha(LocalDate.now());
    }

    @Test
    void guardar() {
        when(iTurnoRepository.save(any(Turno.class))).thenReturn(turno);

        Turno resultado = impServiceTurno.guardar(turno);

        System.out.println("Resultado de guardar: " + resultado);

        assertNotNull(resultado);
        assertEquals(turno, resultado);
        verify(iTurnoRepository).save(turno);
    }

    @Test
    void buscarPorId() {
        Long id = 1L;
        when(iTurnoRepository.findById(id)).thenReturn(Optional.of(turno));

        Turno resultado = impServiceTurno.buscarPorId(id);

        System.out.println("Resultado de buscarPorId: " + resultado);

        assertNotNull(resultado);
        assertEquals(turno, resultado);
    }

    @Test
    void eliminar() {
        Long id = 1L;
        when(iTurnoRepository.existsById(id)).thenReturn(true);

        boolean resultado = impServiceTurno.eliminar(id);

        System.out.println("Resultado de eliminar: " + resultado);

        assertTrue(resultado);
        verify(iTurnoRepository).deleteById(id);
    }

    @Test
    void actualizar() {
        when(iTurnoRepository.existsById(turno.getId())).thenReturn(true);
        when(iTurnoRepository.save(any(Turno.class))).thenReturn(turno);

        Turno resultado = impServiceTurno.actualizar(turno);

        System.out.println("Resultado de actualizar: " + resultado);

        assertNotNull(resultado);
        assertEquals(turno, resultado);
        verify(iTurnoRepository).save(turno);
    }

    @Test
    void listarTodos() {
        List<Turno> turnos = Arrays.asList(turno, turno);
        when(iTurnoRepository.findAll()).thenReturn(turnos);

        List<Turno> resultado = impServiceTurno.listarTodos();

        System.out.println("Resultado de listarTodos: " + resultado);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(turno, resultado.get(0));
    }

    @Test
    void listarTodos_sinTurnos() {
        when(iTurnoRepository.findAll()).thenReturn(List.of());
        System.out.println("No se encontraron turnos");
        assertThrows(EntityNotFoundException.class, () -> impServiceTurno.listarTodos());
        verify(iTurnoRepository).findAll();
    }

    @Test
    void buscarPorNombreDelPaciente() {
        String nombrePaciente = "Juan";
        List<Turno> turnos = Arrays.asList(turno, turno);
        when(iTurnoRepository.findByNombreDelPaciente(nombrePaciente)).thenReturn(turnos);

        List<Turno> resultado = impServiceTurno.buscarPorNombreDelPaciente(nombrePaciente);

        System.out.println("Resultado de buscarPorNombreDelPaciente: " + resultado);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorNombreDelOdontologo() {
        String nombreOdontologo = "Dr. Smith";
        List<Turno> turnos = Arrays.asList(turno, turno);
        when(iTurnoRepository.findByNombreDelOdontologo(nombreOdontologo)).thenReturn(turnos);

        List<Turno> resultado = impServiceTurno.buscarPorNombreDelOdontologo(nombreOdontologo);

        System.out.println("Resultado de buscarPorNombreDelOdontologo: " + resultado);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }
}
