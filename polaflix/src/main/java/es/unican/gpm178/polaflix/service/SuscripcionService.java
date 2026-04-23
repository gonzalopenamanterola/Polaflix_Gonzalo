package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.TipoSuscripcion;
import es.unican.gpm178.polaflix.repository.TipoSuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuscripcionService {

    @Autowired
    private TipoSuscripcionRepository suscripcionRepository;

    public List<TipoSuscripcion> obtenerTodasLasSuscripciones() {
        return suscripcionRepository.findAll();
    }

    public Optional<TipoSuscripcion> obtenerSuscripcionPorId(Long id) {
        return suscripcionRepository.findById(id);
    }

    public TipoSuscripcion crearSuscripcion(TipoSuscripcion suscripcion) {
        validarSuscripcion(suscripcion);
        return suscripcionRepository.save(suscripcion);
    }

    public Optional<TipoSuscripcion> actualizarSuscripcion(Long id, TipoSuscripcion suscripcionActualizada) {
        return suscripcionRepository.findById(id).map(suscripcion -> 
            suscripcionRepository.save(suscripcionActualizada)
        );
    }

    public boolean eliminarSuscripcion(Long id) {
        if (suscripcionRepository.existsById(id)) {
            suscripcionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarSuscripcion(TipoSuscripcion suscripcion) {
        if (suscripcion == null) {
            throw new IllegalArgumentException("La suscripción no puede ser nula");
        }
    }
}
