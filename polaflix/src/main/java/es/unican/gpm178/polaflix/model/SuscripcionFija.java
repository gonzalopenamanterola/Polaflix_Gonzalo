package es.unican.gpm178.polaflix.model;
import lombok.Data;
import jakarta.persistence.Column;
import java.util.List;

@Data
public class SuscripcionFija extends TipoSuscripcion {
    
    @Column(nullable = false)
    private double cuota = 20.0;

    @Override
    public double calcularCostoMensual(List<Visualizacion> vistas) {
        return cuota; // Tarifa plana
    }
}