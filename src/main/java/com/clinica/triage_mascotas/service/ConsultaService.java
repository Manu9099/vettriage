package com.clinica.triage_mascotas.service;


import com.clinica.triage_mascotas.model.*;
import com.clinica.triage_mascotas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TriageRepository triageRepository;

    // Asignar doctor automáticamente según nivel de prioridad
    public Consulta asignarConsulta(Long triageId) {

        // 1. Buscar el triaje
        Triage triage = triageRepository.findById(triageId)
                .orElseThrow(() -> new RuntimeException("Triaje no encontrado"));

        // 2. Seleccionar doctor según prioridad
        Doctor doctor = seleccionarDoctor(triage.getNivelPrioridad());

        // 3. Crear la consulta
        Consulta consulta = new Consulta();
        consulta.setTriage(triage);
        consulta.setDoctor(doctor);

        // 4. Actualizar estado del triaje
        triage.setEstadoConsulta("ASIGNADA");
        triageRepository.save(triage);

        return consultaRepository.save(consulta);
    }

    // Lógica de selección de doctor
    private Doctor seleccionarDoctor(String nivelPrioridad) {

        // Urgente → buscar cirujano o médico general disponible
        if ("URGENTE".equals(nivelPrioridad)) {
            return doctorRepository.findByEspecialidad("Cirugía Veterinaria")
                    .stream().filter(Doctor::isDisponible).findFirst()
                    .orElseGet(this::primerDoctorDisponible);
        }

        // Moderado o Leve → primer doctor disponible
        return primerDoctorDisponible();
    }

    private Doctor primerDoctorDisponible() {
        return doctorRepository.findFirstByDisponibleTrue()
                .orElseThrow(() -> new RuntimeException("No hay doctores disponibles en este momento"));
    }

    // Finalizar consulta
    public Consulta finalizarConsulta(Long consultaId, String notas) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        consulta.setEstado("FINALIZADA");
        consulta.setNotas(notas);

        // Actualizar triaje
        consulta.getTriage().setEstadoConsulta("ATENDIDA");
        triageRepository.save(consulta.getTriage());

        return consultaRepository.save(consulta);
    }

    // Consultas por doctor
    public List<Consulta> consultasPorDoctor(Long doctorId) {
        return consultaRepository.findByDoctorId(doctorId);
    }

    // Consultas pendientes/asignadas
    public List<Consulta> consultasPorEstado(String estado) {
        return consultaRepository.findByEstado(estado.toUpperCase());
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }
    public Consulta cambiarEstado(Long consultaId, String nuevoEstado) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
        consulta.setEstado(nuevoEstado.toUpperCase());

        // Sincronizar con el triaje
        if ("EN_CURSO".equals(nuevoEstado.toUpperCase())) {
            consulta.getTriage().setEstadoConsulta("EN_CURSO");
            triageRepository.save(consulta.getTriage());
        }
        if ("FINALIZADA".equals(nuevoEstado.toUpperCase())) {
            consulta.getTriage().setEstadoConsulta("ATENDIDA");
            triageRepository.save(consulta.getTriage());
        }

        return consultaRepository.save(consulta);
    }


}
