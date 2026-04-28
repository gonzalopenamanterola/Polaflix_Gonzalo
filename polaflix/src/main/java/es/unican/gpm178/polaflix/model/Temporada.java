package es.unican.gpm178.polaflix.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "temporadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int numTemporada;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Capitulo> capitulos = new HashSet<>();
}
