package es.unican.gpm178.polaflix.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "capitulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private int numCapitulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String enlace;

    @ManyToOne
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;
}
