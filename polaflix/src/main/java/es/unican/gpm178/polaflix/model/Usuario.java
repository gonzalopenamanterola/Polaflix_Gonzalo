package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
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

    public void seleccionarSerieVisualizar() { }
}