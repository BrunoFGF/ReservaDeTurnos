package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.model.Domicilio;
import com.hd.ProyectoIntegrador.repository.IDomicilioRepository;
import com.hd.ProyectoIntegrador.service.IServiceDomicilio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImpServiceDomicilio implements IServiceDomicilio {
    @Autowired
    private IDomicilioRepository iDomicilioRepository;
    @Override
    public Domicilio guardar(Domicilio domicilio) {
        return iDomicilioRepository.save(domicilio);
    }

    @Override
    public Domicilio buscarPorId(Long id) {
        Optional<Domicilio> domicilioBuscado = iDomicilioRepository.findById(id);
        return domicilioBuscado.orElse(null);
    }

    @Override
    public boolean eliminar(Long id) {
        if (iDomicilioRepository.existsById(id)) {
            iDomicilioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        if (iDomicilioRepository.existsById(domicilio.getId())) {
            return iDomicilioRepository.save(domicilio);
        } else {
            return null;
        }
    }

    @Override
    public List<Domicilio> listarTodos() {
        List<Domicilio> domicilios = iDomicilioRepository.findAll();
        if (domicilios.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron domicilios");
        }
        return domicilios;
    }

    @Override
    public Domicilio buscarPorNombreDelPaciente(String nombre) {
        return iDomicilioRepository.findByNombreDelPaciente(nombre);
    }

    @Override
    public Domicilio buscarPorApellidoDelPaciente(String apellido) {
        return iDomicilioRepository.findByApellidoDelPaciente(apellido);
    }
}