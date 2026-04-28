package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemporadaDTO {

    @JsonView(Vistas.TemporadaResumen.class)
    private Long id;

    @JsonView(Vistas.TemporadaResumen.class)
    @NotNull(message = "El número de temporada es obligatorio")
    @Min(value = 1, message = "El número de temporada debe ser mayor que 0")
    private Integer numTemporada;

    @JsonView(Vistas.TemporadaCompleto.class)
    private SerieDTO serie;

    @JsonView(Vistas.TemporadaCompleto.class)
    private Set<CapituloDTO> capitulos;
}
