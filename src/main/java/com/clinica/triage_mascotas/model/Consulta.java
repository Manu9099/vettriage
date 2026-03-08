package com.clinica.triage_mascotas.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "triage_id", nullable = false)
    private Triage triage;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private String estado; // ASIGNADA, EN_CURSO, FINALIZADA

    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaAtencion;

    private String notas;

    @PrePersist
    public void prePersist() {
        this.fechaAsignacion = LocalDateTime.now();
        this.estado = "ASIGNADA";
    }

    // ---- GETTERS ----
    public Long getId() { return id; }
    public Triage getTriage() { return triage; }
    public Doctor getDoctor() { return doctor; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public String getNotas() { return notas; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setTriage(Triage triage) { this.triage = triage; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }
    public void setNotas(String notas) { this.notas = notas; }
}