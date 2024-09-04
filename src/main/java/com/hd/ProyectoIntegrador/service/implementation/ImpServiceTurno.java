package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.model.Turno;
import com.hd.ProyectoIntegrador.repository.ITurnoRepository;
import com.hd.ProyectoIntegrador.service.IServiceTurno;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImpServiceTurno implements IServiceTurno {

    @Autowired
    private ITurnoRepository iTurnoRepository;

    @Override
    public Turno guardar(Turno turno) {
        return iTurnoRepository.save(turno);
    }

    @Override
    public Turno buscarPorId(Long id) {
        Optional<Turno> turnoBuscado = iTurnoRepository.findById(id);
        return turnoBuscado.orElse(null);
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = iTurnoRepository.findAll();
        if (turnos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron turnos");
        }
        return turnos;
    }
}
