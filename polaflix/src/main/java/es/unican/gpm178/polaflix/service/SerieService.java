package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> obtenerTodasLasSeries() {
        return serieRepository.findAll();
    }

    public Optional<Serie> obtenerSeriePorId(int id) {
        return serieRepository.findById(id);
    }

    public Serie crearSerie(Serie serie) {
        validarSerie(serie);
        return serieRepository.save(serie);
    }

    public Optional<Serie> actualizarSerie(int id, Serie serieActualizada) {
        return serieRepository.findById(id).map(serie -> {
            if (serieActualizada.getTitulo() != null) {
                serie.setTitulo(serieActualizada.getTitulo());
            }
            if (serieActualizada.getSinopsis() != null) {
                serie.setSinopsis(serieActualizada.getSinopsis());
            }
            if (serieActualizada.getCategoria() != null) {
                serie.setCategoria(serieActualizada.getCategoria());
            }
            if (serieActualizada.getCreadores() != null) {
                serie.setCreadores(serieActualizada.getCreadores());
            }
            if (serieActualizada.getActores() != null) {
                serie.setActores(serieActualizada.getActores());
            }
            serie.setInicial(serieActualizada.getInicial());
            return serieRepository.save(serie);
        });
    }

    public boolean eliminarSerie(int id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarSerie(Serie serie) {
        if (serie.getTitulo() == null || serie.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título de la serie no puede estar vacío");
        }
        if (serie.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría de la serie no puede ser nula");
        }
    }
}
