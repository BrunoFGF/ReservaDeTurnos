package com.hd.ProyectoIntegrador.repository;

import com.hd.ProyectoIntegrador.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
