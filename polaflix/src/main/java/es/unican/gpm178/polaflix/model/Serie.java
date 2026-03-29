package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {

    @Id
    private int id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    private char inicial;

    
    @ElementCollection
    @CollectionTable(name = "serie_creadores", joinColumns = @JoinColumn(name = "serie_id"))
    @Column(name = "creador", nullable = false)
    private List<String> creadores = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "serie_actores", joinColumns = @JoinColumn(name = "serie_id"))
    @Column(name = "actor")
    private List<String> actores = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Temporada> temporadas = new ArrayList<>();

    public Capitulo getUltimoCapitulo() { return null; }
    public boolean esSerieTerminada(List<Visualizacion> historiaUsuario) { return false; }
    public Capitulo buscarCapituloTemporada(int idTemporada, int idCapitulo) { return null; }
}