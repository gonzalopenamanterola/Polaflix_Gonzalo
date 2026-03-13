package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TipoSuscripcion {

    private Long id;

    public abstract double calcularCostoMensual(List<Visualizacion> vistas);
}