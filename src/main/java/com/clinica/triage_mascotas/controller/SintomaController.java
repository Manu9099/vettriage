package com.clinica.triage_mascotas.controller;

import com.clinica.triage_mascotas.model.Sintoma;
import com.clinica.triage_mascotas.repository.SintomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sintomas")
@CrossOrigin(origins = "*")
public class SintomaController {

    @Autowired
    private SintomaRepository sintomaRepository;

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Sintoma>> listar() {
        return ResponseEntity.ok(sintomaRepository.findAll());
    }

    // Listar por categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Sintoma>> porCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(sintomaRepository.findByCategoria(categoria.toUpperCase()));
    }
}