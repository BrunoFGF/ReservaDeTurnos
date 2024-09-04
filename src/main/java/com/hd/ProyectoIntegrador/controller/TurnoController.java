package com.hd.ProyectoIntegrador.controller;

import com.hd.ProyectoIntegrador.model.Paciente;
import com.hd.ProyectoIntegrador.model.Turno;
import com.hd.ProyectoIntegrador.service.IServiceTurno;
import com.hd.ProyectoIntegrador.service.implementation.ImpServiceTurno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private IServiceTurno iServiceTurno;

    public TurnoController() {
        this.iServiceTurno = new ImpServiceTurno();
    }

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
}
