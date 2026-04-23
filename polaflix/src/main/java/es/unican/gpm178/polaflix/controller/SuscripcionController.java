package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.model.TipoSuscripcion;
import es.unican.gpm178.polaflix.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin(origins = "*")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<List<TipoSuscripcion>> obtenerTodasLasSuscripciones() {
        List<TipoSuscripcion> suscripciones = suscripcionService.obtenerTodasLasSuscripciones();
        return ResponseEntity.ok(suscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSuscripcion> obtenerSuscripcionPorId(@PathVariable Long id) {
        Optional<TipoSuscripcion> suscripcion = suscripcionService.obtenerSuscripcionPorId(id);
        return suscripcion.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoSuscripcion> crearSuscripcion(@RequestBody TipoSuscripcion suscripcion) {
        TipoSuscripcion nuevaSuscripcion = suscripcionService.crearSuscripcion(suscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSuscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoSuscripcion> actualizarSuscripcion(@PathVariable Long id, @RequestBody TipoSuscripcion suscripcion) {
        Optional<TipoSuscripcion> suscripcionActualizada = suscripcionService.actualizarSuscripcion(id, suscripcion);
        return suscripcionActualizada.map(ResponseEntity::ok)
                                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSuscripcion(@PathVariable Long id) {
        if (suscripcionService.eliminarSuscripcion(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
