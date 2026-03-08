package com.clinica.triage_mascotas.repository;



import com.clinica.triage_mascotas.model.Triage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TriageRepository extends JpaRepository<Triage, Long> {
    List<Triage> findByPacienteId(Long pacienteId);
    List<Triage> findByNivelPrioridad(String nivelPrioridad);
}