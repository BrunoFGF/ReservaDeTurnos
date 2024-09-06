package com.hd.ProyectoIntegrador.repository;

import com.hd.ProyectoIntegrador.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDomicilioRepository extends JpaRepository<Domicilio, Long> {
    @Query("SELECT d FROM Domicilio d WHERE d.paciente.nombre = :nombre")
    Domicilio findByNombreDelPaciente(@Param("nombre") String nombre);

    @Query("SELECT d FROM Domicilio d WHERE d.paciente.apellido = :apellido")
    Domicilio findByApellidoDelPaciente(@Param("apellido") String apellido);
}
