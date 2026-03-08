package com.clinica.triage_mascotas.repository;

import com.clinica.triage_mascotas.model.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long> {
    List<HistorialClinico> findByPacienteIdOrderByFechaDesc(Long pacienteId);
    List<HistorialClinico> findByDoctorId(Long doctorId);
}