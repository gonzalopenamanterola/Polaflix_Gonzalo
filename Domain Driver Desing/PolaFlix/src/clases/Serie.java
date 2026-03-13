package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {

    private int id;

    private String titulo;

    private String sinopsis;

    private char inicial;

    private List<String> creadores = new ArrayList<>();

    private List<String> actores = new ArrayList<>();

    private Categoria categoria;

    private List<Temporada> temporadas = new ArrayList<>();

    public Capitulo getUltimoCapitulo() { return null; }
    public boolean esSerieTerminada(List<Visualizacion> historiaUsuario) { return false; }
    public Capitulo buscarCapituloTemporada(int idTemporada, int idCapitulo) { return null; }
}