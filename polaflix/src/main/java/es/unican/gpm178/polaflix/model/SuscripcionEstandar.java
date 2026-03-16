package es.unican.gpm178.polaflix.model;
import java.util.List;

public class SuscripcionEstandar extends TipoSuscripcion {
    
    @Override
    public double calcularCostoMensual(List<Visualizacion> vistas) {
        return 0.0;
    }
}