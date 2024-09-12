package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Turno;
import com.hd.ProyectoIntegrador.service.IServiceTurno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private IServiceTurno iServiceTurno;

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(iServiceTurno.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        return ResponseEntity.ok(iServiceTurno.guardar(turno));
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos() {
        return ResponseEntity.ok(iServiceTurno.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable  Long id){
        ResponseEntity<Void> response = null;
        if (iServiceTurno.eliminar(id))
            response = ResponseEntity.status(HttpStatus.OK).build();
        return response;
    }

    @PutMapping
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        Turno turnoeActualizado = iServiceTurno.actualizar(turno);
        return ResponseEntity.ok(turnoeActualizado);
    }

    @GetMapping("nombrePaciente/{nombrePaciente}")
    public ResponseEntity<List<Turno>> buscarPorNombreDelPaciente(@PathVariable String nombrePaciente) {
        return ResponseEntity.ok(iServiceTurno.buscarPorNombreDelPaciente(nombrePaciente));
    }

    @GetMapping("nombreOdontologo/{nombreOdontologo}")
    public ResponseEntity<List<Turno>> buscarPorNombreDelOdontologo(@PathVariable String nombreOdontologo) {
        return ResponseEntity.ok(iServiceTurno.buscarPorNombreDelOdontologo(nombreOdontologo));
    }
}
