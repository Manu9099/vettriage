package com.clinica.triage_mascotas.controller;



import com.clinica.triage_mascotas.model.Paciente;
import com.clinica.triage_mascotas.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")

@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // Registrar nuevo paciente
    @PostMapping
    public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.registrar(paciente));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
    }
}