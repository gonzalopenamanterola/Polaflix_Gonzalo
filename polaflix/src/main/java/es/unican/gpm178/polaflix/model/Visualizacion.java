package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "visualizaciones")
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private Date fechaHora;

    @Column(nullable = false)
    private boolean facturado;

    @Column(nullable = false)
    private int idTemporada;

    @Column(nullable = false)
    private int idCapitulo;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private Factura factura;
    
    public Capitulo getCapitulo() {
        if (serie == null) {
            return null;
        }
        return serie.buscarCapituloTemporada(idTemporada, idCapitulo);
    }
}