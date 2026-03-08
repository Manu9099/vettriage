package com.clinica.triage_mascotas.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "doctores")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especialidad;
    private String telefono;
    private String email;
    private boolean disponible;
    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Consulta> consultas;

    // ---- GETTERS ----
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public boolean isDisponible() { return disponible; }
    public List<Consulta> getConsultas() { return consultas; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas; }
}
