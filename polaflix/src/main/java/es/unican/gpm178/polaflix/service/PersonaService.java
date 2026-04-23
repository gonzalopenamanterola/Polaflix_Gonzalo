package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.Persona;
import es.unican.gpm178.polaflix.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    public Optional<Persona> obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }

    public Persona crearPersona(Persona persona) {
        validarPersona(persona);
        return personaRepository.save(persona);
    }

    public Optional<Persona> actualizarPersona(Long id, Persona personaActualizada) {
        return personaRepository.findById(id).map(persona -> {
            if (personaActualizada.getNombre() != null) {
                persona.setNombre(personaActualizada.getNombre());
            }
            if (personaActualizada.getApellido() != null) {
                persona.setApellido(personaActualizada.getApellido());
            }
            return personaRepository.save(persona);
        });
    }

    public boolean eliminarPersona(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarPersona(Persona persona) {
        if (persona.getNombre() == null || persona.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la persona no puede estar vacío");
        }
        if (persona.getApellido() == null || persona.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido de la persona no puede estar vacío");
        }
    }
}
