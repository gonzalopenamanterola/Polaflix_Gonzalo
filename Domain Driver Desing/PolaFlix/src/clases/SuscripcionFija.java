package clases;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import java.util.List;

@Data
public class SuscripcionFija extends TipoSuscripcion {
    
    private double cuota = 20.0;

    @Override
    public double calcularCostoMensual(List<Visualizacion> vistas) {
        return cuota;
    }
}