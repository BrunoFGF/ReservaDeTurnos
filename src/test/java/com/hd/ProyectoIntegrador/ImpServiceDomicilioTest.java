package com.hd.ProyectoIntegrador;

import com.hd.ProyectoIntegrador.model.Domicilio;
import com.hd.ProyectoIntegrador.repository.IDomicilioRepository;
import com.hd.ProyectoIntegrador.service.implementation.ImpServiceDomicilio;
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

class ImpServiceDomicilioTest {

    @Mock
    private IDomicilioRepository iDomicilioRepository;

    @InjectMocks
    private ImpServiceDomicilio impServiceDomicilio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Av. Siempre Viva");
        domicilio.setNumero(742);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Illinois");

        when(iDomicilioRepository.save(any(Domicilio.class))).thenReturn(domicilio);

        Domicilio resultado = impServiceDomicilio.guardar(domicilio);

        assertNotNull(resultado);
        assertEquals(domicilio, resultado);
        verify(iDomicilioRepository).save(domicilio);

        System.out.println("Domicilio guardado: " + resultado);
    }

    @Test
    void buscarPorId() {
        Long id = 1L;
        Domicilio domicilio = new Domicilio();
        domicilio.setId(id);
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Shelbyville");
        domicilio.setProvincia("Illinois");

        when(iDomicilioRepository.findById(id)).thenReturn(Optional.of(domicilio));

        Domicilio resultado = impServiceDomicilio.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(domicilio, resultado);
        verify(iDomicilioRepository).findById(id);

        System.out.println("Domicilio buscado por ID: " + resultado);
    }

    @Test
    void eliminar() {
        Long id = 2L;
        when(iDomicilioRepository.existsById(id)).thenReturn(true);

        boolean resultado = impServiceDomicilio.eliminar(id);

        assertTrue(resultado);
        verify(iDomicilioRepository).deleteById(id);

        System.out.println("Domicilio eliminado con ID: " + id);
    }

    @Test
    void eliminar_domicilioNoExistente() {
        Long id = 3L;
        when(iDomicilioRepository.existsById(id)).thenReturn(false);

        boolean resultado = impServiceDomicilio.eliminar(id);

        assertFalse(resultado);
        verify(iDomicilioRepository, never()).deleteById(id);

        System.out.println("Intento de eliminación fallido para ID inexistente: " + id);
    }

    @Test
    void actualizar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setId(4L);
        domicilio.setCalle("Av. Principal");
        domicilio.setNumero(456);
        domicilio.setLocalidad("Capital City");
        domicilio.setProvincia("Missouri");

        when(iDomicilioRepository.existsById(domicilio.getId())).thenReturn(true);
        when(iDomicilioRepository.save(any(Domicilio.class))).thenReturn(domicilio);

        Domicilio resultado = impServiceDomicilio.actualizar(domicilio);

        assertNotNull(resultado);
        assertEquals(domicilio, resultado);
        verify(iDomicilioRepository).save(domicilio);

        System.out.println("Domicilio actualizado: " + resultado);
    }

    @Test
    void actualizar_domicilioNoExistente() {
        Domicilio domicilio = new Domicilio();
        domicilio.setId(5L);
        domicilio.setCalle("Calle Secundaria");
        domicilio.setNumero(789);
        domicilio.setLocalidad("Ogdenville");
        domicilio.setProvincia("Oregon");

        when(iDomicilioRepository.existsById(domicilio.getId())).thenReturn(false);

        Domicilio resultado = impServiceDomicilio.actualizar(domicilio);

        assertNull(resultado);
        verify(iDomicilioRepository, never()).save(domicilio);

        System.out.println("Intento de actualización fallido para ID inexistente: " + domicilio.getId());
    }

    @Test
    void listarTodos() {
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setId(6L);
        domicilio1.setCalle("Calle 1");
        domicilio1.setNumero(101);
        domicilio1.setLocalidad("North Haverbrook");
        domicilio1.setProvincia("Utah");

        Domicilio domicilio2 = new Domicilio();
        domicilio2.setId(7L);
        domicilio2.setCalle("Calle 2");
        domicilio2.setNumero(202);
        domicilio2.setLocalidad("Cypress Creek");
        domicilio2.setProvincia("Nevada");

        when(iDomicilioRepository.findAll()).thenReturn(Arrays.asList(domicilio1, domicilio2));

        List<Domicilio> resultado = impServiceDomicilio.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(iDomicilioRepository).findAll();

        System.out.println("Lista de domicilios: " + resultado);
    }

    @Test
    void listarTodos_sinDomicilios() {
        when(iDomicilioRepository.findAll()).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> impServiceDomicilio.listarTodos());
        verify(iDomicilioRepository).findAll();

        System.out.println("No se encontraron domicilios en la base de datos.");
    }

    @Test
    void buscarPorNombreDelPaciente() {
        String nombre = "Juan";
        Domicilio domicilio = new Domicilio();
        domicilio.setId(8L);
        domicilio.setCalle("Calle 3");
        domicilio.setNumero(303);
        domicilio.setLocalidad("Monroe City");
        domicilio.setProvincia("Iowa");

        when(iDomicilioRepository.findByNombreDelPaciente(nombre)).thenReturn(domicilio);

        Domicilio resultado = impServiceDomicilio.buscarPorNombreDelPaciente(nombre);

        assertNotNull(resultado);
        assertEquals(domicilio, resultado);
        verify(iDomicilioRepository).findByNombreDelPaciente(nombre);

        System.out.println("Domicilio buscado por nombre del paciente: " + resultado);
    }

    @Test
    void buscarPorApellidoDelPaciente() {
        String apellido = "Pérez";
        Domicilio domicilio = new Domicilio();
        domicilio.setId(9L);
        domicilio.setCalle("Calle 4");
        domicilio.setNumero(404);
        domicilio.setLocalidad("West Springfield");
        domicilio.setProvincia("Massachusetts");

        when(iDomicilioRepository.findByApellidoDelPaciente(apellido)).thenReturn(domicilio);

        Domicilio resultado = impServiceDomicilio.buscarPorApellidoDelPaciente(apellido);

        assertNotNull(resultado);
        assertEquals(domicilio, resultado);
        verify(iDomicilioRepository).findByApellidoDelPaciente(apellido);

        System.out.println("Domicilio buscado por apellido del paciente: " + resultado);
    }
}
