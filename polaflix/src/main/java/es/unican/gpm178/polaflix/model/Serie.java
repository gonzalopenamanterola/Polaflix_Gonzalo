package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Serie(int id, String titulo, String sinopsis, char inicial, 
                Set<String> creadores, Set<String> actores, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.inicial = inicial;
        this.creadores = creadores != null ? creadores : new HashSet<>();
        this.actores = actores != null ? actores : new HashSet<>();
        this.categoria = categoria;
    }
}