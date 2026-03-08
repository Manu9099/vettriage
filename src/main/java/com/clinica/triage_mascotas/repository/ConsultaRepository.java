package com.clinica.triage_mascotas.repository;


import com.clinica.triage_mascotas.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByDoctorId(Long doctorId);
    List<Consulta> findByEstado(String estado);
    List<Consulta> findByTriageId(Long triageId);
}