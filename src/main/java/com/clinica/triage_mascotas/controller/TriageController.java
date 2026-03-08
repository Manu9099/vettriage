package com.clinica.triage_mascotas.controller;


import com.clinica.triage_mascotas.model.Triage;
import com.clinica.triage_mascotas.service.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/triage")
@CrossOrigin(origins = "*")
public class TriageController {

    @Autowired
    private TriageService triageService;

    // Realizar triaje a un paciente
    @PostMapping("/realizar")
    public ResponseEntity<Triage> realizar(@RequestBody Map<String, Object> body) {
        Long pacienteId = Long.valueOf(body.get("pacienteId").toString());
        List<Integer> ids = (List<Integer>) body.get("sintomaIds");
        List<Long> sintomaIds = ids.stream().map(Long::valueOf).toList();
        return ResponseEntity.ok(triageService.realizarTriage(pacienteId, sintomaIds));
    }

    // Listar todos los triajes
    @GetMapping
    public ResponseEntity<List<Triage>> listar() {
        return ResponseEntity.ok(triageService.listarTodos());
    }

    // Historial de triajes por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Triage>> porPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(triageService.listarPorPaciente(pacienteId));
    }

    // Solo urgentes
    @GetMapping("/urgentes")
    public ResponseEntity<List<Triage>> urgentes() {
        return ResponseEntity.ok(triageService.listarUrgentes());
    }

    // Buscar triaje por id
    @GetMapping("/{id}")
    public ResponseEntity<Triage> porId(@PathVariable Long id) {
        return ResponseEntity.ok(triageService.buscarPorId(id));
    }
}
