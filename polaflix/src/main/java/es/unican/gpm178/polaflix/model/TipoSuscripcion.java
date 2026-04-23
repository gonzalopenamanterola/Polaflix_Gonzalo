package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos_suscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_plan", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TipoSuscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 

    public abstract double calcularCostoMensual();
}