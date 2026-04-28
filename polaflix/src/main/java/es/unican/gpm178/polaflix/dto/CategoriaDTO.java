package es.unican.gpm178.polaflix.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum CategoriaDTO {
    @JsonView(Vistas.Public.class)
    ESTANDAR,
    
    @JsonView(Vistas.Public.class)
    SILVER,
    
    @JsonView(Vistas.Public.class)
    GOLD
}
