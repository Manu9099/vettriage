package com.clinica.triage_mascotas.service;


import com.clinica.triage_mascotas.model.*;
import com.clinica.triage_mascotas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TriageService {

    @Autowired
    private TriageRepository triageRepository;

    @Autowired
    private SintomaRepository sintomaRepository;

    @Autowired
    private PacienteService pacienteService;

    public Triage realizarTriage(Long pacienteId, List<Long> sintomaIds) {

        // 1. Buscar paciente
        Paciente paciente = pacienteService.buscarPorId(pacienteId);

        // 2. Buscar síntomas seleccionados
        List<Sintoma> sintomas = sintomaRepository.findAllById(sintomaIds);

        // 3. Calcular puntaje total
        int puntaje = sintomas.stream()
                .mapToInt(Sintoma::getPesoGravedad)
                .sum();

        // 4. Clasificar nivel de prioridad
        String nivel = clasificarNivel(puntaje);

        // 5. Generar diagnóstico probable
        String diagnostico = generarDiagnostico(sintomas, paciente);

        // 6. Generar recomendación
        String recomendacion = generarRecomendacion(nivel);

        // 7. Armar y guardar el triaje
        Triage triage = new Triage();
        triage.setPaciente(paciente);
        triage.setSintomas(sintomas);
        triage.setPuntajeTotal(puntaje);
        triage.setNivelPrioridad(nivel);
        triage.setDiagnosticoProbable(diagnostico);
        triage.setRecomendacion(recomendacion);

        return triageRepository.save(triage);
    }

    // -----------------------------------------------
    // LÓGICA DE CLASIFICACIÓN
    // -----------------------------------------------
    private String clasificarNivel(int puntaje) {
        if (puntaje >= 15) return "URGENTE";
        if (puntaje >= 7)  return "MODERADO";
        return "LEVE";
    }

    // -----------------------------------------------
    // LÓGICA DE DIAGNÓSTICO
    // -----------------------------------------------
    private String generarDiagnostico(List<Sintoma> sintomas, Paciente paciente) {

        long respiratorio = contarCategoria(sintomas, "RESPIRATORIO");
        long digestivo    = contarCategoria(sintomas, "DIGESTIVO");
        long neurologico  = contarCategoria(sintomas, "NEUROLOGICO");
        long traumatico   = contarCategoria(sintomas, "TRAUMATICO");
        long general      = contarCategoria(sintomas, "GENERAL");

        // Detectar categoría dominante
        long max = Math.max(respiratorio, Math.max(digestivo,
                Math.max(neurologico, Math.max(traumatico, general))));

        if (max == 0) return "Sin síntomas suficientes para diagnóstico";

        if (max == neurologico)
            return "Posible trastorno neurológico. Se requiere evaluación inmediata.";

        if (max == respiratorio)
            return "Posible afección respiratoria aguda. Evaluar vías aéreas.";

        if (max == traumatico)
            return "Trauma físico detectado. Revisar lesiones externas e internas.";

        if (max == digestivo)
            return "Posible afección gastrointestinal. Evaluar hidratación y dolor abdominal.";

        if (max == general)
            return "Síntomas generales de malestar sistémico. Evaluar signos vitales.";

        return "Evaluación clínica requerida para diagnóstico definitivo.";
    }

    private long contarCategoria(List<Sintoma> sintomas, String categoria) {
        return sintomas.stream()
                .filter(s -> categoria.equalsIgnoreCase(s.getCategoria()))
                .count();
    }

    // -----------------------------------------------
    // LÓGICA DE RECOMENDACIÓN
    // -----------------------------------------------
    private String generarRecomendacion(String nivel) {
        return switch (nivel) {
            case "URGENTE"  -> "⚠️ Atención INMEDIATA requerida. Pasar a sala de emergencias.";
            case "MODERADO" -> "🕐 Atención en las próximas 2 a 4 horas. Mantener en observación.";
            default         -> "✅ Consulta normal. Asignar turno con médico disponible.";
        };
    }

    // -----------------------------------------------
    // CONSULTAS
    // -----------------------------------------------
    public List<Triage> listarTodos() {
        return triageRepository.findAll();
    }

    public List<Triage> listarPorPaciente(Long pacienteId) {
        return triageRepository.findByPacienteId(pacienteId);
    }

    public List<Triage> listarUrgentes() {
        return triageRepository.findByNivelPrioridad("URGENTE");
    }

    public Triage buscarPorId(Long id) {
        return triageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Triaje no encontrado con id: " + id));
    }
}