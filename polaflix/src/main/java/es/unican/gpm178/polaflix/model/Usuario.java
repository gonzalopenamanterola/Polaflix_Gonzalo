package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_login")
    private List<Factura> facturas = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "suscripcion_id")
    private TipoSuscripcion plan;

    @ManyToMany
    @JoinTable(
        name = "usuario_series_pendientes",
        joinColumns = @JoinColumn(name = "usuario_login"),
        inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private List<Serie> seriesPendientes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "usuario_series_empezadas",
        joinColumns = @JoinColumn(name = "usuario_login"),
        inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private List<Serie> seriesEmpezadas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "usuario_series_terminadas",
        joinColumns = @JoinColumn(name = "usuario_login"),
        inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private List<Serie> seriesTerminadas = new ArrayList<>();

    // Métodos de comportamiento
    public void agregarSeriePendiente(Serie s) { this.seriesPendientes.add(s); }
    public void marcarCapituloVisto(Capitulo c) { }
    public void seleccionarSerieVisualizar() { }
}