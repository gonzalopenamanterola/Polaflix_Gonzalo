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
public class Usuario {

    private String login;

    private String password;

    private String iban;
   
    private List<Factura> facturas = new ArrayList<>();

    private TipoSuscripcion plan;

    private List<Serie> seriesPendientes = new ArrayList<>();

    private List<Serie> seriesEmpezadas = new ArrayList<>();

    private List<Serie> seriesTerminadas = new ArrayList<>();

    public void agregarSeriePendiente(Serie s) { this.seriesPendientes.add(s); }
    public void marcarCapituloVisto(Capitulo c) {  }
    public void seleccionarSerieVisualizar() { }
}