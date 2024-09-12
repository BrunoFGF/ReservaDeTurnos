package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Domicilio;
import com.hd.ProyectoIntegrador.service.IServiceDomicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {

    @Autowired
    private IServiceDomicilio iServiceDomicilio;

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(iServiceDomicilio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Domicilio> guardar(@RequestBody Domicilio domicilio) {
        return ResponseEntity.ok(iServiceDomicilio.guardar(domicilio));
    }

    @GetMapping
    public ResponseEntity<List<Domicilio>> listarTodos() {
        return ResponseEntity.ok(iServiceDomicilio.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable  Long id){
        ResponseEntity<Void> response = null;
        if (iServiceDomicilio.eliminar(id))
            response = ResponseEntity.status(HttpStatus.OK).build();
        return response;
    }

    @PutMapping
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio) {
        Domicilio domicilioActualizado = iServiceDomicilio.actualizar(domicilio);
        return ResponseEntity.ok(domicilioActualizado);
    }
}
