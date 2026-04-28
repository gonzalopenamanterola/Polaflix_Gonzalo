package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.dto.*;
import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.service.SerieService;
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
@RequestMapping("/api/series")
@CrossOrigin(origins = "*")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping
    @JsonView(Vistas.SerieResumen.class)
    public ResponseEntity<List<SerieDTO>> obtenerTodasLasSeries() {
        List<Serie> series = serieService.obtenerTodasLasSeries();
        List<SerieDTO> seriesDTO = series.stream()
                .map(this::toResumenDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seriesDTO);
    }

    @GetMapping("/{id}")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> obtenerSeriePorId(@PathVariable int id) {
        Optional<Serie> serie = serieService.obtenerSeriePorId(id);
        return serie.map(s -> ResponseEntity.ok(toCompletoDTO(s)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> crearSerie(@Valid @RequestBody SerieDTO serieDTO) {
        Serie serie = dtoMapper.toSerie(serieDTO);
        Serie nuevaSerie = serieService.crearSerie(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCompletoDTO(nuevaSerie));
    }

    @PutMapping("/{id}")
    @JsonView(Vistas.SerieCompleto.class)
    public ResponseEntity<SerieDTO> actualizarSerie(@PathVariable int id, @Valid @RequestBody SerieDTO serieDTO) {
        Serie serie = dtoMapper.toSerie(serieDTO);
        Optional<Serie> serieActualizada = serieService.actualizarSerie(id, serie);
        return serieActualizada.map(s -> ResponseEntity.ok(toCompletoDTO(s)))
                               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSerie(@PathVariable int id) {
        if (serieService.eliminarSerie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Métodos privados para convertir a DTO
    private SerieDTO toResumenDTO(Serie serie) {
        if (serie == null) {
            return null;
        }
        SerieDTO dto = new SerieDTO();
        dto.setId(serie.getId());
        dto.setTitulo(serie.getTitulo());
        dto.setInicial(serie.getInicial());
        dto.setCategoria(dtoMapper.toCategoriaDTO(serie.getCategoria()));
        return dto;
    }

    private SerieDTO toCompletoDTO(Serie serie) {
        if (serie == null) {
            return null;
        }
        SerieDTO dto = new SerieDTO();
        dto.setId(serie.getId());
        dto.setTitulo(serie.getTitulo());
        dto.setSinopsis(serie.getSinopsis());
        dto.setInicial(serie.getInicial());
        dto.setCreadores(serie.getCreadores());
        dto.setActores(serie.getActores());
        dto.setCategoria(dtoMapper.toCategoriaDTO(serie.getCategoria()));
        return dto;
    }
}
