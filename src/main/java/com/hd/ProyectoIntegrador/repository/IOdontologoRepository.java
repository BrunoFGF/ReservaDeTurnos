package com.hd.ProyectoIntegrador.repository;

import com.hd.ProyectoIntegrador.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
    Odontologo findByMatricula (String matricula);
}
