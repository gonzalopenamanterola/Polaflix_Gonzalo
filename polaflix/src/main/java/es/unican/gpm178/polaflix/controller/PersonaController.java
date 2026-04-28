package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.dto.*;
import es.unican.gpm178.polaflix.model.Persona;
import es.unican.gpm178.polaflix.service.PersonaService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping
    @JsonView(Vistas.PersonaResumen.class)
    public ResponseEntity<List<PersonaDTO>> obtenerTodasLasPersonas() {
        List<Persona> personas = personaService.obtenerTodasLasPersonas();
        List<PersonaDTO> personasDTO = personas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(personasDTO);
    }

    @GetMapping("/{id}")
    @JsonView(Vistas.PersonaCompleto.class)
    public ResponseEntity<PersonaDTO> obtenerPersonaPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPersonaPorId(id);
        return persona.map(p -> ResponseEntity.ok(toDTO(p)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @JsonView(Vistas.PersonaCompleto.class)
    public ResponseEntity<PersonaDTO> crearPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        Persona persona = dtoMapper.toPersona(personaDTO);
        Persona nuevaPersona = personaService.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(nuevaPersona));
    }

    @PutMapping("/{id}")
    @JsonView(Vistas.PersonaCompleto.class)
    public ResponseEntity<PersonaDTO> actualizarPersona(@PathVariable Long id, @Valid @RequestBody PersonaDTO personaDTO) {
        Persona persona = dtoMapper.toPersona(personaDTO);
        Optional<Persona> personaActualizada = personaService.actualizarPersona(id, persona);
        return personaActualizada.map(p -> ResponseEntity.ok(toDTO(p)))
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        if (personaService.eliminarPersona(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método privado para convertir a DTO
    private PersonaDTO toDTO(Persona persona) {
        if (persona == null) {
            return null;
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        return dto;
    }
}
