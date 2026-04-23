package es.unican.gpm178.polaflix.repository;

import es.unican.gpm178.polaflix.model.TipoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSuscripcionRepository extends JpaRepository<TipoSuscripcion, Long> {
}
