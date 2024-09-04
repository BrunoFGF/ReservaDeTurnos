package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.service.IServiceOdontologo;
import com.hd.ProyectoIntegrador.service.implementation.ImpServiceOdontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private IServiceOdontologo serviceOdontologo;

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serviceOdontologo.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(serviceOdontologo.guardar(odontologo));
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(serviceOdontologo.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable  Long id){
        ResponseEntity response = null;
        if (serviceOdontologo.eliminar(id)){
            response = ResponseEntity.status(HttpStatus.OK).build();
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) {
        Odontologo odontologoActualizado = serviceOdontologo.actualizar(odontologo);
        if (odontologoActualizado != null) {
            return ResponseEntity.ok(odontologoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(serviceOdontologo.buscarPorMatricula(matricula));
    }

    /*@GetMapping("matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) {
        Od  ontologo odontologo = serviceOdontologo.buscarPorMatricula(matricula);
        if (odontologo != null) {
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/


}
