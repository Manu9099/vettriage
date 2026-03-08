package com.clinica.triage_mascotas.repository;

import com.clinica.triage_mascotas.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByDisponibleTrue();
    Optional<Doctor> findFirstByDisponibleTrue();
    List<Doctor> findByEspecialidad(String especialidad);
}