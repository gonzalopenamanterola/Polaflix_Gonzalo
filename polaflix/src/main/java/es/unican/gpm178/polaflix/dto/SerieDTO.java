package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerieDTO {

    @JsonView(Vistas.SerieResumen.class)
    private Integer id;

    @JsonView(Vistas.SerieResumen.class)
    @NotBlank(message = "El título de la serie no puede estar vacío")
    private String titulo;

    @JsonView(Vistas.SerieCompleto.class)
    private String sinopsis;

    @JsonView(Vistas.SerieResumen.class)
    private Character inicial;

    @JsonView(Vistas.SerieCompleto.class)
    private Set<String> creadores;

    @JsonView(Vistas.SerieCompleto.class)
    private Set<String> actores;

    @JsonView(Vistas.SerieResumen.class)
    @NotNull(message = "La categoría de la serie es obligatoria")
    private CategoriaDTO categoria;
}
