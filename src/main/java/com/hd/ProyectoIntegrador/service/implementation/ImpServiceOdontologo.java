package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.repository.IOdontologoRepository;
import com.hd.ProyectoIntegrador.service.IServiceOdontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImpServiceOdontologo implements IServiceOdontologo {

    @Autowired
    private IOdontologoRepository iOdontologoRepository;

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        if (odontologo.getNombre() == null || odontologo.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del odontólogo no puede estar vacío");
        }
        if (odontologo.getApellido() == null || odontologo.getApellido().isEmpty()) {
            throw new BadRequestException("El apellido del odontólogo no puede estar vacío");
        }
        if (odontologo.getMatricula() == null || odontologo.getMatricula().isEmpty()) {
            throw new BadRequestException("La matrícula del odontólogo es obligatoria");
        }
        return iOdontologoRepository.save(odontologo);
    }

    @Override
    public Odontologo buscarPorId(Long id){
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        Optional<Odontologo> odontologoBuscado = iOdontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            return odontologoBuscado.get();
        } else {
            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        if (iOdontologoRepository.existsById(id)) {
            iOdontologoRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + id);
        }
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            throw new BadRequestException("El ID del odontólogo es obligatorio para actualizar");
        }
        if (odontologo.getNombre() == null || odontologo.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre del odontólogo no puede estar vacío");
        }
        if (odontologo.getApellido() == null || odontologo.getApellido().isEmpty()) {
            throw new BadRequestException("El apellido del odontólogo no puede estar vacío");
        }
        if (odontologo.getMatricula() == null || odontologo.getMatricula().isEmpty()) {
            throw new BadRequestException("La matrícula del odontólogo es obligatoria");
        }

        if (iOdontologoRepository.existsById(odontologo.getId())) {
            return iOdontologoRepository.save(odontologo);
        } else {
            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + odontologo.getId());
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = iOdontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontráron odontólogos");
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorMatricula(String matricula) {
        if (matricula == null || matricula.isEmpty()) {
            throw new BadRequestException("La matrícula proporcionada no es válida");
        }
        Odontologo odontologoBuscado = iOdontologoRepository.findByMatricula(matricula);
        if (odontologoBuscado != null) {
            return odontologoBuscado;
        } else {
            throw new ResourceNotFoundException("No se encontró ningún odontólogo con la matrícula: " + matricula);
        }
    }
}