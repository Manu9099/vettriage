package com.clinica.triage_mascotas.service;

import com.clinica.triage_mascotas.model.*;
import com.clinica.triage_mascotas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository historialRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Registrar entrada en historial
    public HistorialClinico registrar(Map<String, Object> body) {

        Long pacienteId = Long.valueOf(body.get("pacienteId").toString());
        Long consultaId = Long.valueOf(body.get("consultaId").toString());
        Long doctorId   = Long.valueOf(body.get("doctorId").toString());

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        HistorialClinico h = new HistorialClinico();
        h.setPaciente(paciente);
        h.setConsulta(consulta);
        h.setDoctor(doctor);
        h.setPesoKg(body.get("pesoKg") != null ? Double.valueOf(body.get("pesoKg").toString()) : null);
        h.setTemperatura(body.get("temperatura") != null ? Double.valueOf(body.get("temperatura").toString()) : null);
        h.setDiagnostico(body.getOrDefault("diagnostico", "").toString());
        h.setTratamiento(body.getOrDefault("tratamiento", "").toString());
        h.setMedicamentos(body.getOrDefault("medicamentos", "").toString());
        h.setObservaciones(body.getOrDefault("observaciones", "").toString());
        h.setProximaVisita(body.getOrDefault("proximaVisita", "").toString());

        return historialRepository.save(h);
    }

    // Historial completo de un paciente
    public List<HistorialClinico> historialPorPaciente(Long pacienteId) {
        return historialRepository.findByPacienteIdOrderByFechaDesc(pacienteId);
    }

    // Historial por doctor
    public List<HistorialClinico> historialPorDoctor(Long doctorId) {
        return historialRepository.findByDoctorId(doctorId);
    }

    public List<HistorialClinico> listarTodos() {
        return historialRepository.findAll();
    }
}