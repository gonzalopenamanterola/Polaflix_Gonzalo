package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.model.Persona;
import es.unican.gpm178.polaflix.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodasLasPersonas() {
        List<Persona> personas = personaService.obtenerTodasLasPersonas();
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPersonaPorId(id);
        return persona.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        Optional<Persona> personaActualizada = personaService.actualizarPersona(id, persona);
        return personaActualizada.map(ResponseEntity::ok)
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        if (personaService.eliminarPersona(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
