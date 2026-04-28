package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapituloDTO {

    @JsonView(Vistas.CapituloResumen.class)
    private Long id;

    @JsonView(Vistas.CapituloResumen.class)
    @NotBlank(message = "El título del capítulo no puede estar vacío")
    private String titulo;

    @JsonView(Vistas.CapituloResumen.class)
    @NotNull(message = "El número del capítulo es obligatorio")
    @Min(value = 1, message = "El número del capítulo debe ser mayor que 0")
    private Integer numCapitulo;

    @JsonView(Vistas.CapituloCompleto.class)
    @NotBlank(message = "La descripción del capítulo no puede estar vacía")
    private String descripcion;

    @JsonView(Vistas.CapituloCompleto.class)
    @NotBlank(message = "El enlace del capítulo no puede estar vacío")
    private String enlace;

    @JsonView(Vistas.CapituloCompleto.class)
    private TemporadaDTO temporada;
}
