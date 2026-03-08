package com.clinica.triage_mascotas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "triajes")
public class Triage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    @JsonIgnoreProperties("consultas")
    private Paciente paciente;

    @ManyToMany
    @JoinTable(
            name = "triage_sintomas",
            joinColumns = @JoinColumn(name = "triage_id"),
            inverseJoinColumns = @JoinColumn(name = "sintoma_id")
    )
    private List<Sintoma> sintomas;

    private Integer puntajeTotal;

    private String nivelPrioridad; // URGENTE, MODERADO, LEVE

    private String diagnosticoProbable;

    private String recomendacion;

    @Column(updatable = false)
    private LocalDateTime fechaTriage;

    private String estadoConsulta; // PENDIENTE, ASIGNADA, ATENDIDA

    @PrePersist
    public void prePersist() {
        this.fechaTriage = LocalDateTime.now();
        this.estadoConsulta = "PENDIENTE";
    }

    // ---- GETTERS ----
    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public List<Sintoma> getSintomas() { return sintomas; }
    public Integer getPuntajeTotal() { return puntajeTotal; }
    public String getNivelPrioridad() { return nivelPrioridad; }
    public String getDiagnosticoProbable() { return diagnosticoProbable; }
    public String getRecomendacion() { return recomendacion; }
    public LocalDateTime getFechaTriage() { return fechaTriage; }
    public String getEstadoConsulta() { return estadoConsulta; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setSintomas(List<Sintoma> sintomas) { this.sintomas = sintomas; }
    public void setPuntajeTotal(Integer puntajeTotal) { this.puntajeTotal = puntajeTotal; }
    public void setNivelPrioridad(String nivelPrioridad) { this.nivelPrioridad = nivelPrioridad; }
    public void setDiagnosticoProbable(String diagnosticoProbable) { this.diagnosticoProbable = diagnosticoProbable; }
    public void setRecomendacion(String recomendacion) { this.recomendacion = recomendacion; }
    public void setFechaTriage(LocalDateTime fechaTriage) { this.fechaTriage = fechaTriage; }
    public void setEstadoConsulta(String estadoConsulta) { this.estadoConsulta = estadoConsulta; }
}