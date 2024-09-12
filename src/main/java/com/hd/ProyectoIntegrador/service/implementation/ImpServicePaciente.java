package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.repository.IPacienteRepository;
import com.hd.ProyectoIntegrador.service.IServicePaciente;
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
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del paciente no puede estar vacío");
        }
        if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
            throw new BadRequestException("El apellido del paciente no puede estar vacío");
        }
        if (paciente.getDni() == null || paciente.getDni().isEmpty()) {
            throw new BadRequestException("El DNI del paciente es obligatorio");
        }
        return iPacienteRepository.save(paciente);
    }

    @Override
    public Paciente buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        Optional<Paciente> paienteBuscado = iPacienteRepository.findById(id);
        if (paienteBuscado.isPresent()) {
            return paienteBuscado.get();
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        if (iPacienteRepository.existsById(id)) {
            iPacienteRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + id);
        }
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        if (paciente.getId() == null) {
            throw new BadRequestException("El ID del paciente es obligatorio para actualizar");
        }
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del paciente no puede estar vacío");
        }
        if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
            throw new BadRequestException("El apellido del paciente no puede estar vacío");
        }
        if (paciente.getDni() == null || paciente.getDni().isEmpty()) {
            throw new BadRequestException("El DNI del paciente es obligatorio");
        }

        if (iPacienteRepository.existsById(paciente.getId())) {
            return iPacienteRepository.save(paciente);
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + paciente.getId());
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = iPacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontráron pacientes");
        }
        return pacientes;
    }

    @Override
    public Paciente buscarPorDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            throw new BadRequestException("El dni proporcionado no es válido");
        }
        Paciente pacienteBuscado = iPacienteRepository.findByDni(dni);
        if (pacienteBuscado != null) {
            return pacienteBuscado;
        } else {
            throw new ResourceNotFoundException("No se encontró ningún paciente con el dni: " + dni);
        }
    }
}