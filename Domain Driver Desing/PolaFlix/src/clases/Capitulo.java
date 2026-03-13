package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo {

    private int id;

    private int numeroCapitulo;

    private String titulo;

    private String descripcion;

    private String enlaceVideo;

    private Temporada temporada;

    public double getPrecio() {
        return 0.0; 
    }
}