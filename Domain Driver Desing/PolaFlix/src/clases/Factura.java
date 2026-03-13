package clases;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {


    private int id;

    private int mes;

    private int año;

    private Date fechaEmision;

    private double importeTotal;

    private List<Visualizacion> visualizaciones = new ArrayList<>();
}