package com.clinica.triage_mascotas.controller;

import com.clinica.triage_mascotas.model.HistorialClinico;
import com.clinica.triage_mascotas.service.HistorialClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService historialService;

    // Registrar entrada en historial
    @PostMapping
    public ResponseEntity<HistorialClinico> registrar(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(historialService.registrar(body));
    }

    // Historial completo de un paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialClinico>> porPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historialService.historialPorPaciente(pacienteId));
    }

    // Historial por doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<HistorialClinico>> porDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(historialService.historialPorDoctor(doctorId));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<HistorialClinico>> listar() {
        return ResponseEntity.ok(historialService.listarTodos());
    }
}