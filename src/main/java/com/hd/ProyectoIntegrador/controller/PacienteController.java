package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Odontologo;
import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.service.IServicePaciente;
import com.hd.ProyectoIntegrador.service.implementation.ImpServicePaciente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    private IServicePaciente servicePaciente;

    public PacienteController() {
        this.servicePaciente = new ImpServicePaciente();
    }

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
        if (servicePaciente.eliminar(id)){
            response = ResponseEntity.status(HttpStatus.OK).build();
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) {
        Paciente pacienteActualizado = servicePaciente.actualizar(paciente);
        if (pacienteActualizado != null) {
            return ResponseEntity.ok(pacienteActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<Paciente> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicePaciente.buscarPorNombre(nombre));
    }

    @GetMapping("apellido/{apellido}")
    public ResponseEntity<Paciente> buscarPorApellido(@PathVariable String apellido) {
        return ResponseEntity.ok(servicePaciente.buscarPorApellido(apellido));
    }

    @GetMapping("dni/{dni}")
    public ResponseEntity<Paciente> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(servicePaciente.buscarPorDni(dni));
    }
}
