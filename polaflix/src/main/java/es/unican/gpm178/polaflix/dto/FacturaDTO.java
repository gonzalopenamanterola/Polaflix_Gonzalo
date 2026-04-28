package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaDTO {

    @JsonView(Vistas.FacturaResumen.class)
    private Integer id;

    @JsonView(Vistas.FacturaResumen.class)
    @NotNull(message = "El mes es obligatorio")
    @Min(value = 1, message = "El mes debe estar entre 1 y 12")
    private Integer mes;

    @JsonView(Vistas.FacturaResumen.class)
    @NotNull(message = "El año es obligatorio")
    @Min(value = 2000, message = "El año debe ser mayor o igual a 2000")
    private Integer año;

    @JsonView(Vistas.FacturaResumen.class)
    @NotNull(message = "La fecha de emisión es obligatoria")
    private Date fechaEmision;

    @JsonView(Vistas.FacturaResumen.class)
    @NotNull(message = "El importe total es obligatorio")
    @Min(value = 0, message = "El importe total debe ser mayor o igual a 0")
    private Double importeTotal;

    @JsonView(Vistas.FacturaCompleto.class)
    private UsuarioDTO usuario;
}
