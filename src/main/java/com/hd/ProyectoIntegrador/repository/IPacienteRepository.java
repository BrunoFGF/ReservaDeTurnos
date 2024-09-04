package com.hd.ProyectoIntegrador.repository;

import com.hd.ProyectoIntegrador.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByNombre (String nombre);
    Paciente findByApellido (String apellido);
    Paciente findByDni (String dni);
}
