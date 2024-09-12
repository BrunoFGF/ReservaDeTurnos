package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.service.IServiceOdontologo;
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
    public ResponseEntity<Void> eliminar(@PathVariable  Long id){
        ResponseEntity<Void> response = null;
        if (serviceOdontologo.eliminar(id))
            response = ResponseEntity.status(HttpStatus.OK).build();
        return response;
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) {
        Odontologo odontologoActualizado = serviceOdontologo.actualizar(odontologo);
        return ResponseEntity.ok(odontologoActualizado);
    }

    @GetMapping("matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(serviceOdontologo.buscarPorMatricula(matricula));
    }
}
