package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int mes;

    @Column(name = "anio", nullable = false)
    private int año;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaEmision;

    @Column(nullable = false)
    private double importeTotal;

    @ElementCollection
    @CollectionTable(
        name = "factura_visualizaciones",
        joinColumns = @JoinColumn(name = "factura_id")
    )
    private List<Visualizacion> visualizaciones = new ArrayList<>();
}