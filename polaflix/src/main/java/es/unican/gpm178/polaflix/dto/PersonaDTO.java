package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaDTO {

    @JsonView(Vistas.Public.class)
    private Long id;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El nombre de la persona no puede estar vacío")
    private String nombre;

    @JsonView(Vistas.Public.class)
    @NotBlank(message = "El apellido de la persona no puede estar vacío")
    private String apellido;
}
