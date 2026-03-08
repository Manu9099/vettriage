package com.clinica.triage_mascotas.service;


import com.clinica.triage_mascotas.model.Usuario;
import com.clinica.triage_mascotas.repository.UsuarioRepository;
import com.clinica.triage_mascotas.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword()))
            throw new RuntimeException("Contraseña incorrecta");

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol());

        return Map.of(
                "token",    token,
                "username", usuario.getUsername(),
                "nombre",   usuario.getNombre(),
                "rol",      usuario.getRol()
        );
    }

    public void cambiarPassword(String username, String passwordActual, String passwordNueva) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(passwordActual, usuario.getPassword()))
            throw new RuntimeException("La contraseña actual es incorrecta");

        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);
    }
}