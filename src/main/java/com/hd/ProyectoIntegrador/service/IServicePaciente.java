package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Paciente;

import java.util.List;

public interface IServicePaciente {
    Paciente guardar (Paciente paciente) throws BadRequestException;
    Paciente buscarPorId(Long id) throws ResourceNotFoundException, BadRequestException;
    boolean eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
    Paciente actualizar (Paciente paciente) throws ResourceNotFoundException, BadRequestException;
    List<Paciente> listarTodos() throws ResourceNotFoundException;
    Paciente buscarPorDni(String dni) throws ResourceNotFoundException, BadRequestException;
}
