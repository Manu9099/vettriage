package com.clinica.triage_mascotas.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;



import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edadMeses;

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0")
    private Double pesoKg;

    private String sexo;

    private boolean esterilizado;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String duenioNombre;

    @NotBlank(message = "El teléfono es obligatorio")
    private String duenioTelefono;

    private String duenioEmail;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // ---- GETTERS ----
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public Integer getEdadMeses() { return edadMeses; }
    public Double getPesoKg() { return pesoKg; }
    public String getSexo() { return sexo; }
    public boolean isEsterilizado() { return esterilizado; }
    public String getDuenioNombre() { return duenioNombre; }
    public String getDuenioTelefono() { return duenioTelefono; }
    public String getDuenioEmail() { return duenioEmail; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setRaza(String raza) { this.raza = raza; }
    public void setEdadMeses(Integer edadMeses) { this.edadMeses = edadMeses; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }
    public void setDuenioNombre(String duenioNombre) { this.duenioNombre = duenioNombre; }
    public void setDuenioTelefono(String duenioTelefono) { this.duenioTelefono = duenioTelefono; }
    public void setDuenioEmail(String duenioEmail) { this.duenioEmail = duenioEmail; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}