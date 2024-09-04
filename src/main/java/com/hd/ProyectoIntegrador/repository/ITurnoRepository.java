package com.hd.ProyectoIntegrador.repository;

import com.hd.ProyectoIntegrador.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
    @Query("SELECT t FROM Turno t WHERE t.paciente.nombre = :nombre")
    List<Turno> findByNombreDelPaciente(@Param("nombre") String nombre);

    @Query("SELECT t FROM Turno t WHERE t.odontologo.nombre = :nombre")
    List<Turno> findByNombreDelOdontologo(@Param("nombre") String nombre);
}
