package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.repository.IPacienteRepository;
import com.hd.ProyectoIntegrador.service.IServicePaciente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImpServicePaciente implements IServicePaciente {
    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Override
    public Paciente guardar(Paciente paciente) {
        return iPacienteRepository.save(paciente);
    }

    @Override
    public Paciente buscarPorId(Long id) {
        Optional<Paciente> pacienteBuscado = iPacienteRepository.findById(id);
        return pacienteBuscado.orElse(null);
    }

    @Override
    public boolean eliminar(Long id) {
        if (iPacienteRepository.existsById(id)) {
            iPacienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        if (iPacienteRepository.existsById(paciente.getId())) {
            return iPacienteRepository.save(paciente);
        } else {
            return null;
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = iPacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pacientes");
        }
        return pacientes;
    }

    @Override
    public Paciente buscarPorNombre(String nombre) {
        return iPacienteRepository.findByNombre(nombre);
    }

    @Override
    public Paciente buscarPorApellido(String apellido) {
        return iPacienteRepository.findByApellido(apellido);
    }

    @Override
    public Paciente buscarPorDni(String dni) {
        return iPacienteRepository.findByDni(dni);
    }
}