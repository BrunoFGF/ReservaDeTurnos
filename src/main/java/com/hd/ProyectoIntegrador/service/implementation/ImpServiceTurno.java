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
    public boolean eliminar(Long id) {
        if (iTurnoRepository.existsById(id)) {
            iTurnoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Turno actualizar(Turno turno) {
        if (iTurnoRepository.existsById(turno.getId())) {
            return iTurnoRepository.save(turno);
        } else {
            return null;
        }
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = iTurnoRepository.findAll();
        if (turnos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron turnos");
        }
        return turnos;
    }

    @Override
    public List<Turno> buscarPorNombreDelPaciente(String nombrePaciente) {
        return iTurnoRepository.findByNombreDelPaciente(nombrePaciente);
    }

    @Override
    public List<Turno> buscarPorNombreDelOdontologo(String nombreOdontologo) {
        return iTurnoRepository.findByNombreDelOdontologo(nombreOdontologo);
    }
}
