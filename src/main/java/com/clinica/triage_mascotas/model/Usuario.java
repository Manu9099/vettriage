package com.clinica.triage_mascotas.model;


import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String rol; // ADMIN, DOCTOR, RECEPCIONISTA

    private String nombre;

    // ---- GETTERS ----
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public String getNombre() { return nombre; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String rol) { this.rol = rol; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
