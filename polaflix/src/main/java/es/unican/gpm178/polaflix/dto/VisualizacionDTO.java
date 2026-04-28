package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisualizacionDTO {

    @JsonView(Vistas.VisualizacionResumen.class)
    private Long id;

    @JsonView(Vistas.VisualizacionResumen.class)
    @NotNull(message = "La fecha de visualización es obligatoria")
    private Date fechaVisualizacion;

    @JsonView(Vistas.VisualizacionResumen.class)
    @NotNull(message = "El minuto actual es obligatorio")
    @Min(value = 0, message = "El minuto actual debe ser mayor o igual a 0")
    private Integer minutoActual;

    @JsonView(Vistas.VisualizacionCompleto.class)
    private UsuarioDTO usuario;

    @JsonView(Vistas.VisualizacionCompleto.class)
    private CapituloDTO capitulo;
}
