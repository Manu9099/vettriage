package com.clinica.triage_mascotas.config;

import com.clinica.triage_mascotas.model.Doctor;
import com.clinica.triage_mascotas.model.Sintoma;
import com.clinica.triage_mascotas.model.Usuario;
import com.clinica.triage_mascotas.repository.DoctorRepository;
import com.clinica.triage_mascotas.repository.SintomaRepository;
import com.clinica.triage_mascotas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SintomaRepository sintomaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Cargar síntomas
        if (sintomaRepository.count() == 0) {
            List<Sintoma> sintomas = List.of(
                    crear("Dificultad para respirar",   "RESPIRATORIO", 10, "Jadeo intenso o respiración con esfuerzo"),
                    crear("Tos persistente",             "RESPIRATORIO",  6, "Tos continua por más de 10 minutos"),
                    crear("Respiración acelerada",       "RESPIRATORIO",  8, "Más de 40 respiraciones por minuto"),
                    crear("Vómitos frecuentes",          "DIGESTIVO",     6, "Más de 3 vómitos en una hora"),
                    crear("Diarrea con sangre",          "DIGESTIVO",     8, "Presencia de sangre en las heces"),
                    crear("Abdomen hinchado",            "DIGESTIVO",     8, "Distensión abdominal visible"),
                    crear("Pérdida de apetito",          "DIGESTIVO",     4, "No come desde hace más de 24 horas"),
                    crear("Convulsiones",                "NEUROLOGICO",  10, "Episodios de convulsión activa"),
                    crear("Desorientación",              "NEUROLOGICO",   7, "No reconoce entorno ni dueño"),
                    crear("Parálisis parcial",           "NEUROLOGICO",   9, "Incapacidad de mover extremidades"),
                    crear("Temblores",                   "NEUROLOGICO",   6, "Temblor muscular involuntario"),
                    crear("Herida abierta",              "TRAUMATICO",    7, "Laceración visible con sangrado"),
                    crear("Fractura visible",            "TRAUMATICO",    8, "Hueso expuesto o deformación"),
                    crear("Sangrado activo",             "TRAUMATICO",    9, "Hemorragia que no cede"),
                    crear("Golpe fuerte",                "TRAUMATICO",    5, "Trauma por accidente o caída"),
                    crear("Fiebre alta",                 "GENERAL",       7, "Temperatura mayor a 39.5°C"),
                    crear("Letargia extrema",            "GENERAL",       7, "Sin respuesta a estímulos"),
                    crear("Desmayo",                     "GENERAL",       9, "Pérdida de conciencia temporal"),
                    crear("Picazón leve",                "GENERAL",       2, "Rascado ocasional sin heridas"),
                    crear("Estornudos ocasionales",      "GENERAL",       2, "Menos de 5 estornudos por hora")
            );
            sintomaRepository.saveAll(sintomas);
            System.out.println("✅ Síntomas cargados correctamente.");

        }


        // Cargar doctores
        if (doctorRepository.count() == 0) {
            List<Doctor> doctores = List.of(
                    crearDoctor("Dra. Valentina Cruz",  "Medicina General",    "3011111111", "v.cruz@clinica.com",    true),
                    crearDoctor("Dr. Andrés Morales",   "Cirugía Veterinaria", "3022222222", "a.morales@clinica.com", true),
                    crearDoctor("Dra. Sofía Reyes",     "Cardiología Animal",  "3033333333", "s.reyes@clinica.com",   false),
                    crearDoctor("Dr. Carlos Vega",      "Dermatología",        "3044444444", "c.vega@clinica.com",    true)
            );
            doctorRepository.saveAll(doctores);
            System.out.println("✅ Doctores cargados correctamente.");
        }

        if (usuarioRepository.count() == 0) {
            List<Usuario> usuarios = List.of(
                    crearUsuario("admin",        "admin123",  "ADMIN",          "Administrador"),
                    crearUsuario("doctor1",      "doctor123", "DOCTOR",         "Dr. Andrés Morales"),
                    crearUsuario("recepcion",    "recep123",  "RECEPCIONISTA",  "Valentina Cruz")
            );
            usuarioRepository.saveAll(usuarios);
            System.out.println("✅ Usuarios cargados correctamente.");
        }


    }

    // ---- Helpers ----
    private Sintoma crear(String nombre, String categoria, int peso, String descripcion) {
        Sintoma s = new Sintoma();
        s.setNombre(nombre);
        s.setCategoria(categoria);
        s.setPesoGravedad(peso);
        s.setDescripcion(descripcion);
        return s;
    }

    private Doctor crearDoctor(String nombre, String especialidad, String telefono, String email, boolean disponible) {
        Doctor d = new Doctor();
        d.setNombre(nombre);
        d.setEspecialidad(especialidad);
        d.setTelefono(telefono);
        d.setEmail(email);
        d.setDisponible(disponible);
        return d;
    }
    private Usuario crearUsuario(String username, String password, String rol, String nombre) {
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRol(rol);
        u.setNombre(nombre);
        return u;


    }

}