package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.model.Turno;

import java.util.List;

public interface IServiceTurno {
    //CRUD
    Turno guardar(Turno turno);
    Turno buscarPorId(Long id);
    boolean eliminar(Long id);
    Turno actualizar (Turno turno);
    List<Turno> listarTodos();
    List<Turno> buscarPorNombreDelPaciente(String nombrePaciente);
    List<Turno> buscarPorNombreDelOdontologo(String nombreOdontologo);
}
