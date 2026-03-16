package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "capitulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo {

    @Id
    private int id;

    @Column(nullable = false)
    private int numeroCapitulo;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 500)
    private String enlaceVideo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    public double getPrecio() {
        return 0.0; // Lógica de cálculo o variable
    }
}