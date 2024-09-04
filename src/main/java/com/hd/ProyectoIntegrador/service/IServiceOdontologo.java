package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.model.Odontologo;

import java.util.List;

public interface IServiceOdontologo {
    Odontologo guardar (Odontologo odontologo);
    Odontologo buscarPorId(Long id);
    boolean eliminar(Long id);
    Odontologo actualizar (Odontologo odontologo);
    List<Odontologo> listarTodos();
    Odontologo buscarPorMatricula(String matricula);
}
