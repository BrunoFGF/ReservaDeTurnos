package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Odontologo;

import java.util.List;

public interface IServiceOdontologo {
    Odontologo guardar (Odontologo odontologo) throws BadRequestException;
    Odontologo buscarPorId(Long id) throws ResourceNotFoundException, BadRequestException;
    boolean eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
    Odontologo actualizar (Odontologo odontologo) throws ResourceNotFoundException, BadRequestException;
    List<Odontologo> listarTodos() throws ResourceNotFoundException;
    Odontologo buscarPorMatricula(String matricula) throws ResourceNotFoundException, BadRequestException;
}