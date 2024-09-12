package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Turno;

import java.util.List;

public interface IServiceTurno {
    //CRUD
    Turno guardar(Turno turno) throws BadRequestException;
    Turno buscarPorId(Long id) throws ResourceNotFoundException, BadRequestException;
    boolean eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
    Turno actualizar (Turno turno) throws ResourceNotFoundException, BadRequestException;
    List<Turno> listarTodos() throws ResourceNotFoundException;
    List<Turno> buscarPorNombreDelPaciente(String nombrePaciente) throws ResourceNotFoundException, BadRequestException;
    List<Turno> buscarPorNombreDelOdontologo(String nombreOdontologo) throws ResourceNotFoundException, BadRequestException;
}
