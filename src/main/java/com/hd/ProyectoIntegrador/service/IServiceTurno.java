package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.model.Turno;

import java.util.List;

public interface IServiceTurno {
    //CRUD
    Turno guardar(Turno turno);
    Turno buscarPorId(Long id);
    List<Turno> listarTodos();
}
