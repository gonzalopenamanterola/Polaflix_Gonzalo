package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "*")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public ResponseEntity<List<Serie>> obtenerTodasLasSeries() {
        List<Serie> series = serieService.obtenerTodasLasSeries();
        return ResponseEntity.ok(series);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> obtenerSeriePorId(@PathVariable int id) {
        Optional<Serie> serie = serieService.obtenerSeriePorId(id);
        return serie.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Serie> crearSerie(@RequestBody Serie serie) {
        Serie nuevaSerie = serieService.crearSerie(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSerie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serie> actualizarSerie(@PathVariable int id, @RequestBody Serie serie) {
        Optional<Serie> serieActualizada = serieService.actualizarSerie(id, serie);
        return serieActualizada.map(ResponseEntity::ok)
                               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSerie(@PathVariable int id) {
        if (serieService.eliminarSerie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
