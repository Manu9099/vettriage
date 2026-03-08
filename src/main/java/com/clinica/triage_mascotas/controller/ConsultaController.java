package com.clinica.triage_mascotas.controller;


import com.clinica.triage_mascotas.model.Consulta;
import com.clinica.triage_mascotas.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Asignar consulta automáticamente
    @PostMapping("/asignar/{triageId}")
    public ResponseEntity<Consulta> asignar(@PathVariable Long triageId) {
        return ResponseEntity.ok(consultaService.asignarConsulta(triageId));
    }

    // Finalizar consulta con notas
    @PutMapping("/finalizar/{consultaId}")
    public ResponseEntity<Consulta> finalizar(
            @PathVariable Long consultaId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(consultaService.finalizarConsulta(
                consultaId, body.get("notas")));
    }

    // Ver consultas de un doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Consulta>> porDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(consultaService.consultasPorDoctor(doctorId));
    }

    // Ver por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Consulta>> porEstado(@PathVariable String estado) {
        return ResponseEntity.ok(consultaService.consultasPorEstado(estado));
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @PutMapping("/estado/{consultaId}")
    public ResponseEntity<Consulta> cambiarEstado(
            @PathVariable Long consultaId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(consultaService.cambiarEstado(
                consultaId, body.get("estado")));
    }

}