package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.repository.IOdontologoRepository;
import com.hd.ProyectoIntegrador.service.IServiceOdontologo;
import jakarta.persistence.EntityNotFoundException;
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
        return iOdontologoRepository.save(odontologo);
    }

    @Override
    public Odontologo buscarPorId(Long id){
        //va a buscar al odontologo y lo va a guardar en odontologoBuscado
        //o va a guardar un null en el odontologoBuscado
        Optional<Odontologo> odontologoBuscado = iOdontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            return odontologoBuscado.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean eliminar(Long id) {
        if (iOdontologoRepository.existsById(id)) {
            iOdontologoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        if (iOdontologoRepository.existsById(odontologo.getId())) {
            return iOdontologoRepository.save(odontologo);
        } else {
            return null;
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = iOdontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron odontólogos");
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorMatricula(String matricula) {
        return iOdontologoRepository.findByMatricula(matricula);
    }

}
