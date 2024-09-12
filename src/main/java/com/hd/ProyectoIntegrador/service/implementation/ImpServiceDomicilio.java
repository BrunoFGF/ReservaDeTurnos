package com.hd.ProyectoIntegrador.service.implementation;

import com.hd.ProyectoIntegrador.exception.BadRequestException;
import com.hd.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.hd.ProyectoIntegrador.model.Domicilio;
import com.hd.ProyectoIntegrador.repository.IDomicilioRepository;
import com.hd.ProyectoIntegrador.service.IServiceDomicilio;
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
        if (domicilio.getCalle() == null || domicilio.getCalle().isEmpty()) {
            throw new BadRequestException("El nombre de la calle no puede estar vacío");
        }
        if (domicilio.getLocalidad() == null || domicilio.getLocalidad().isEmpty()) {
            throw new BadRequestException("El apellido de la localidad no puede estar vacío");
        }
        if (domicilio.getProvincia() == null || domicilio.getProvincia().isEmpty()) {
            throw new BadRequestException("El nombre de la provincia no puede estar vacío");
        }
        return iDomicilioRepository.save(domicilio);
    }

    @Override
    public Domicilio buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        Optional<Domicilio> domicilioBuscado = iDomicilioRepository.findById(id);
        if (domicilioBuscado.isPresent()) {
            return domicilioBuscado.get();
        } else {
            throw new ResourceNotFoundException("No se encontró el domicilio con id " + id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID proporcionado no es válido");
        }
        if (iDomicilioRepository.existsById(id)) {
            iDomicilioRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("No se encontró el domicilio con id " + id);
        }
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        if (domicilio.getId() == null) {
            throw new BadRequestException("El ID del domicilio es obligatorio para actualizar");
        }
        if (domicilio.getCalle() == null || domicilio.getCalle().isEmpty()) {
            throw new BadRequestException("El nombre de la calle no puede estar vacío");
        }
        if (domicilio.getLocalidad() == null || domicilio.getLocalidad().isEmpty()) {
            throw new BadRequestException("El apellido de la localidad no puede estar vacío");
        }
        if (domicilio.getProvincia() == null || domicilio.getProvincia().isEmpty()) {
            throw new BadRequestException("El nombre de la provincia no puede estar vacío");
        }
        if (iDomicilioRepository.existsById(domicilio.getId())) {
            return iDomicilioRepository.save(domicilio);
        } else {
            throw new ResourceNotFoundException("No se encontró el domicilio con id " + domicilio.getId());
        }
    }

    @Override
    public List<Domicilio> listarTodos() {
        List<Domicilio> domicilios = iDomicilioRepository.findAll();
        if (domicilios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontráron domicilios");
        }
        return domicilios;
    }
}