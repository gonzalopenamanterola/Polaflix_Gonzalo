package es.unican.gpm178.polaflix.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visualizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visualizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date fechaVisualizacion;

    @Column(nullable = false)
    private int minutoActual;

    @ManyToOne
    @JoinColumn(name = "usuario_login", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "capitulo_id", nullable = false)
    private Capitulo capitulo;
}
