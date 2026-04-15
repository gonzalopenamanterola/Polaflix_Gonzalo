package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String iban;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Factura> facturas = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "suscripcion_id")
    private TipoSuscripcion plan;

    @ManyToMany
    @JoinTable(name = "usuario_series_pendientes", 
    joinColumns = @JoinColumn(name = "usuario_login"), 
    inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private Set<Serie> seriesPendientes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "usuario_series_empezadas", 
    joinColumns = @JoinColumn(name = "usuario_login"), 
    inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private Set<Serie> seriesEmpezadas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "usuario_series_terminadas", 
    joinColumns = @JoinColumn(name = "usuario_login"), 
    inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private Set<Serie> seriesTerminadas = new HashSet<>();

    // Métodos de comportamiento
    public void agregarSeriePendiente(Serie s) {
        if (s == null) {
            return;
        }
        if (seriesEmpezadas.contains(s) || seriesTerminadas.contains(s)) {
            return;
        }
        seriesPendientes.add(s);
    }

    public void marcarCapituloVisto(Capitulo c) {
        if (c == null || c.getTemporada() == null) {
            return;
        }

        Serie serie = c.getTemporada().getSerie();
        if (serie == null) {
            return;
        }

        seriesPendientes.remove(serie);
        seriesEmpezadas.add(serie);

        Capitulo ultimo = serie.getUltimoCapitulo();
        if (ultimo != null && ultimo.getId() == c.getId()) {
            seriesEmpezadas.remove(serie);
            seriesTerminadas.add(serie);
        }
    }

    public void seleccionarSerieVisualizar() {
        // Este método se mantiene como punto de extensión del modelo,
        // pero la lógica de estado se gestiona en los conjuntos de series.
    }
}