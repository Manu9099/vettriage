package com.clinica.triage_mascotas.repository;


import com.clinica.triage_mascotas.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Buscar por nombre de la mascota
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por nombre del dueño
    List<Paciente> findByDuenioNombreContainingIgnoreCase(String duenioNombre);
}
