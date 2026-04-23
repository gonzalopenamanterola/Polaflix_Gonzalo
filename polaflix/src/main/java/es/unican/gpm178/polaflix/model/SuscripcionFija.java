package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;

@Data
@EqualsAndHashCode(callSuper = false)
public class SuscripcionFija extends TipoSuscripcion {
    
    @Column(nullable = false)
    private double cuota = 20.0;

    @Override
    public double calcularCostoMensual() {
        return cuota;
    }
}