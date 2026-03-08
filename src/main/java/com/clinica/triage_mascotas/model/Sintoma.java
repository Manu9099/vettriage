package com.clinica.triage_mascotas.model;


import jakarta.persistence.*;

@Entity
@Table(name = "sintomas")
public class Sintoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String categoria; // RESPIRATORIO, DIGESTIVO, NEUROLOGICO, TRAUMATICO, GENERAL

    @Column(nullable = false)
    private Integer pesoGravedad; // 1 al 10

    private String descripcion;

    // ---- GETTERS ----
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public Integer getPesoGravedad() { return pesoGravedad; }
    public String getDescripcion() { return descripcion; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPesoGravedad(Integer pesoGravedad) { this.pesoGravedad = pesoGravedad; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
