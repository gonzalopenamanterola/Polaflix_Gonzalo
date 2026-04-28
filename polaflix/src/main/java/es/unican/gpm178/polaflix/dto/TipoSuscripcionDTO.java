package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoSuscripcionDTO {

    @JsonView(Vistas.TipoSuscripcionResumen.class)
    private Long id;

    @JsonView(Vistas.TipoSuscripcionResumen.class)
    private String tipo;

    @JsonView(Vistas.TipoSuscripcionCompleto.class)
    private Double costoMensual;
}
