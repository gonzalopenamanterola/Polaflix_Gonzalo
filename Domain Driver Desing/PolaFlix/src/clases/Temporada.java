package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {

    private int id;

    private int numeroTemporada;

    private Date fechaEstreno;

    private Serie serie;

    private List<Capitulo> capitulos = new ArrayList<>();
}