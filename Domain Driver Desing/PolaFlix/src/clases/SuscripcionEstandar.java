package clases;
import javax.persistence.DiscriminatorValue;
import java.util.List;

public class SuscripcionEstandar extends TipoSuscripcion {
    
    @Override
    public double calcularCostoMensual(List<Visualizacion> vistas) {

        return 1;
    }
}