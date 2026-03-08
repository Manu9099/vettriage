package com.clinica.triage_mascotas.service;


import com.clinica.triage_mascotas.model.Paciente;
import com.clinica.triage_mascotas.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class PacienteService {

    @Autowired
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrar(Paciente paciente) {
        System.out.println(">>> Paciente recibido: " + paciente.getNombre()
                + " | especie: " + paciente.getEspecie()
                + " | duenio: " + paciente.getDuenioNombre());
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    public Paciente actualizar(Long id, Paciente datos) {
        Paciente paciente = buscarPorId(id);
        paciente.setNombre(datos.getNombre());
        paciente.setEspecie(datos.getEspecie());
        paciente.setRaza(datos.getRaza());
        paciente.setEdadMeses(datos.getEdadMeses());
        paciente.setPesoKg(datos.getPesoKg());
        paciente.setSexo(datos.getSexo());
        paciente.setEsterilizado(datos.isEsterilizado());
        paciente.setDuenioNombre(datos.getDuenioNombre());
        paciente.setDuenioTelefono(datos.getDuenioTelefono());
        paciente.setDuenioEmail(datos.getDuenioEmail());
        return pacienteRepository.save(paciente);
    }

    public void eliminar(Long id) {
        buscarPorId(id); // valida que existe
        pacienteRepository.deleteById(id);
    }


    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
}