package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.model.Paciente;

import java.util.List;

public interface IServicePaciente {
    Paciente guardar (Paciente paciente);
    Paciente buscarPorId(Long id);
    boolean eliminar(Long id);
    Paciente actualizar (Paciente paciente);
    List<Paciente> listarTodos();
}
