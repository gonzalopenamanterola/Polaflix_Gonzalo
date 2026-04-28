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
public class UsuarioDTO {

    @JsonView(Vistas.UsuarioResumen.class)
    @NotBlank(message = "El login del usuario no puede estar vacío")
    private String login;

    @JsonView(Vistas.UsuarioCompleto.class)
    @NotBlank(message = "La contraseña del usuario no puede estar vacía")
    private String password;

    @JsonView(Vistas.UsuarioCompleto.class)
    @NotBlank(message = "El IBAN del usuario no puede estar vacío")
    private String iban;

    @JsonView(Vistas.UsuarioCompleto.class)
    private TipoSuscripcionDTO plan;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesPendientes;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesEmpezadas;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<SerieDTO> seriesTerminadas;

    @JsonView(Vistas.UsuarioCompleto.class)
    private Set<FacturaDTO> facturas;
}
