package com.clinica.triage_mascotas.repository;



import com.clinica.triage_mascotas.model.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {
    List<Sintoma> findByCategoria(String categoria);
}
