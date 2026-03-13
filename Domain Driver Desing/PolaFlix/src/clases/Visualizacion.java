package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {

    private Date fechaHora;

    private boolean facturado;

    private int idTemporada;

    private int idCapitulo;

    private Serie serie;
    
    public Capitulo getCapitulo() {
        return null;
    }
}