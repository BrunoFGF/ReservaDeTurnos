package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.service.IServicePaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private IServicePaciente servicePaciente;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicePaciente.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(servicePaciente.guardar(paciente));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(servicePaciente.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable  Long id){
        ResponseEntity<Void> response = null;
        if (servicePaciente.eliminar(id))
            response = ResponseEntity.status(HttpStatus.OK).build();
        return response;
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) {
        Paciente pacienteActualizado = servicePaciente.actualizar(paciente);
        return ResponseEntity.ok(pacienteActualizado);
    }

    @GetMapping("dni/{dni}")
    public ResponseEntity<Paciente> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(servicePaciente.buscarPorDni(dni));
    }
}
