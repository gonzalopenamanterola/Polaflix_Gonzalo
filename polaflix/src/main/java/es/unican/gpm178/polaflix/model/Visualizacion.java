package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaHora;;

    @Column(nullable = false)
    private boolean facturado;

    @Column(nullable = false)
    private int idTemporada;

    @Column(nullable = false)
    private int idCapitulo;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;
    
    public Capitulo getCapitulo() {
        return null; 
    }
}