package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    private char inicial;

    @ElementCollection
    @CollectionTable(name = "serie_creadores", joinColumns = @JoinColumn(name = "serie_id"))
    @Column(name = "creador", nullable = false)
    private Set<String> creadores = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "serie_actores", joinColumns = @JoinColumn(name = "serie_id"))
    @Column(name = "actor")
    private Set<String> actores = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Temporada> temporadas = new ArrayList<>();

    public Capitulo getUltimoCapitulo() {
        if (temporadas == null || temporadas.isEmpty()) {
            return null;
        }

        Temporada ultimaTemporada = temporadas.stream()
                .max(Comparator.comparingInt(Temporada::getNumeroTemporada))
                .orElse(null);

        if (ultimaTemporada == null || ultimaTemporada.getCapitulos() == null || ultimaTemporada.getCapitulos().isEmpty()) {
            return null;
        }

        return ultimaTemporada.getCapitulos()
                .stream()
                .max(Comparator.comparingInt(Capitulo::getNumeroCapitulo))
                .orElse(null);
    }

    public boolean esSerieTerminada(List<Visualizacion> historiaUsuario) {
        if (historiaUsuario == null || historiaUsuario.isEmpty()) {
            return false;
        }

        Capitulo ultimo = getUltimoCapitulo();
        if (ultimo == null) {
            return false;
        }

        return historiaUsuario.stream()
                .anyMatch(v -> v.getSerie() != null
                        && v.getSerie().equals(this)
                        && v.getCapitulo() != null
                        && v.getCapitulo().getId() == ultimo.getId());
    }

    public Capitulo buscarCapituloTemporada(int idTemporada, int idCapitulo) {
        if (temporadas == null) {
            return null;
        }

        return temporadas.stream()
                .filter(t -> t.getId() == idTemporada)
                .findFirst()
                .flatMap(t -> t.getCapitulos().stream()
                        .filter(c -> c.getId() == idCapitulo)
                        .findFirst())
                .orElse(null);
    }
}