package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Domicilio;

import java.util.List;

public interface IServiceDomicilio {
    Domicilio guardar (Domicilio domicilio) throws BadRequestException;
    Domicilio buscarPorId(Long id) throws ResourceNotFoundException, BadRequestException;
    boolean eliminar(Long id) throws ResourceNotFoundException, BadRequestException;
    Domicilio actualizar (Domicilio domicilio) throws ResourceNotFoundException, BadRequestException;
    List<Domicilio> listarTodos() throws ResourceNotFoundException;
}
