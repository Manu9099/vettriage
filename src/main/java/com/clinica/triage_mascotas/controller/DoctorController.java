package com.clinica.triage_mascotas.controller;

import com.clinica.triage_mascotas.model.Doctor;
import com.clinica.triage_mascotas.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctores")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<List<Doctor>> listar() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Doctor>> disponibles() {
        return ResponseEntity.ok(doctorRepository.findByDisponibleTrue());
    }
}