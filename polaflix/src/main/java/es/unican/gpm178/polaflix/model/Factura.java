package es.unican.gpm178.polaflix.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int mes;

    @Column(name = "anio", nullable = false)
    private int año;

    @Column(nullable = false)
    private Date fechaEmision;

    @Column(nullable = false)
    private double importeTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_login")
    private Usuario usuario;

    public Factura(int id, int mes, int año, Date fechaEmision, double importeTotal, Usuario usuario) {
        this.id = id;
        this.mes = mes;
        this.año = año;
        this.fechaEmision = fechaEmision;
        this.importeTotal = importeTotal;
        this.usuario = usuario;
    }
}