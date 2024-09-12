package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.model.Turno;
import com.hd.ProyectoIntegrador.repository.ITurnoRepository;
import com.hd.ProyectoIntegrador.service.IServiceTurno;
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
        if (turno.getPaciente() == null){
            throw new BadRequestException("El turno debe tener un paciente asignado");
        }
        if (turno.getFecha() == null) {
            throw new BadRequestException("El turno debe tener una fecha asignada");
        }
        if (turno.getOdontologo() == null) {
            throw new BadRequestException("El turno debe tener un odontólogo asignado");
        }
        return iTurnoRepository.save(turno);
    }

    @Override
    public Turno buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        Optional<Turno> turnoBuscado = iTurnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            return turnoBuscado.get();
        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id " + id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        if (iTurnoRepository.existsById(id)) {
            iTurnoRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id " + id);
        }
    }

    @Override
    public Turno actualizar(Turno turno) {
        if (turno.getId() == null) {
            throw new BadRequestException("El ID del turno es obligatorio para actualizar");
        }
        if (turno.getPaciente() == null){
            throw new BadRequestException("El turno debe tener un paciente asignado");
        }
        if (turno.getFecha() == null) {
            throw new BadRequestException("El turno debe tener una fecha asignada");
        }
        if (turno.getOdontologo() == null) {
            throw new BadRequestException("El turno debe tener un odontólogo asignado");
        }

        if (iTurnoRepository.existsById(turno.getId())) {
            return iTurnoRepository.save(turno);
        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id " + turno.getId());
        }
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = iTurnoRepository.findAll();
        if (turnos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontráron turnos");
        }
        return turnos;
    }

    @Override
    public List<Turno> buscarPorNombreDelPaciente(String nombrePaciente) {
        if (nombrePaciente == null || nombrePaciente.isEmpty()) {
            throw new BadRequestException("El nombre del paciente proporcionado no es válido");
        }
        List<Turno> turnosBuscados = iTurnoRepository.findByNombreDelPaciente(nombrePaciente);
        if (turnosBuscados != null) {
            return turnosBuscados;
        } else {
            throw new ResourceNotFoundException("No se encontró ningún turno con el paciente: " + nombrePaciente);
        }
    }

    @Override
    public List<Turno> buscarPorNombreDelOdontologo(String nombreOdontologo) {
        if (nombreOdontologo == null || nombreOdontologo.isEmpty()) {
            throw new BadRequestException("El nombre del odontólogo proporcionado no es válido");
        }
        List<Turno> turnosBuscados = iTurnoRepository.findByNombreDelOdontologo(nombreOdontologo);
        if (turnosBuscados != null) {
            return turnosBuscados;
        } else {
            throw new ResourceNotFoundException("No se encontró ningún turno con el odontólogo: " + nombreOdontologo);
        }
    }
}
