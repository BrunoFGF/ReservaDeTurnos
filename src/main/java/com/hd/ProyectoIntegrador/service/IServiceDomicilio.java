package com.hd.ProyectoIntegrador.service;

import com.hd.ProyectoIntegrador.model.Domicilio;

import java.util.List;

public interface IServiceDomicilio {
    Domicilio guardar (Domicilio domicilio);
    Domicilio buscarPorId(Long id);
    boolean eliminar(Long id);
    Domicilio actualizar (Domicilio domicilio);
    List<Domicilio> listarTodos();
    Domicilio buscarPorNombreDelPaciente(String nombre);
    Domicilio buscarPorApellidoDelPaciente(String apellido);
}
