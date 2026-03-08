package com.clinica.triage_mascotas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "historial_clinico")
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    @JsonIgnoreProperties({"triage", "doctor"})
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties("consultas")
    private Doctor doctor;

    private Double pesoKg;
    private Double temperatura;      // en grados Celsius
    private String diagnostico;
    private String tratamiento;
    private String medicamentos;
    private String observaciones;
    private String proximaVisita;

    @Column(updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }

    // ---- GETTERS ----
    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Consulta getConsulta() { return consulta; }
    public Doctor getDoctor() { return doctor; }
    public Double getPesoKg() { return pesoKg; }
    public Double getTemperatura() { return temperatura; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getMedicamentos() { return medicamentos; }
    public String getObservaciones() { return observaciones; }
    public String getProximaVisita() { return proximaVisita; }
    public LocalDateTime getFecha() { return fecha; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setProximaVisita(String proximaVisita) { this.proximaVisita = proximaVisita; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}