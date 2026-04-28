package es.unican.gpm178.polaflix.dto;

import es.unican.gpm178.polaflix.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DTOMapper {

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setLogin(usuario.getLogin());
        dto.setPassword(usuario.getPassword());
        dto.setIban(usuario.getIban());
        
        if (usuario.getPlan() != null) {
            dto.setPlan(toTipoSuscripcionDTO(usuario.getPlan()));
        }
        
        if (usuario.getSeriesPendientes() != null) {
            dto.setSeriesPendientes(usuario.getSeriesPendientes().stream()
                    .map(this::toSerieDTO)
                    .collect(Collectors.toSet()));
        }
        
        if (usuario.getSeriesEmpezadas() != null) {
            dto.setSeriesEmpezadas(usuario.getSeriesEmpezadas().stream()
                    .map(this::toSerieDTO)
                    .collect(Collectors.toSet()));
        }
        
        if (usuario.getSeriesTerminadas() != null) {
            dto.setSeriesTerminadas(usuario.getSeriesTerminadas().stream()
                    .map(this::toSerieDTO)
                    .collect(Collectors.toSet()));
        }
        
        if (usuario.getFacturas() != null) {
            dto.setFacturas(usuario.getFacturas().stream()
                    .map(this::toFacturaDTO)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }

    public Usuario toUsuario(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getLogin());
        usuario.setPassword(dto.getPassword());
        usuario.setIban(dto.getIban());
        return usuario;
    }

    public SerieDTO toSerieDTO(Serie serie) {
        if (serie == null) {
            return null;
        }
        SerieDTO dto = new SerieDTO();
        dto.setId(serie.getId());
        dto.setTitulo(serie.getTitulo());
        dto.setSinopsis(serie.getSinopsis());
        dto.setInicial(serie.getInicial());
        dto.setCreadores(serie.getCreadores());
        dto.setActores(serie.getActores());
        dto.setCategoria(toCategoriaDTO(serie.getCategoria()));
        return dto;
    }

    public Serie toSerie(SerieDTO dto) {
        if (dto == null) {
            return null;
        }
        Serie serie = new Serie();
        serie.setId(dto.getId());
        serie.setTitulo(dto.getTitulo());
        serie.setSinopsis(dto.getSinopsis());
        serie.setInicial(dto.getInicial());
        serie.setCreadores(dto.getCreadores());
        serie.setActores(dto.getActores());
        if (dto.getCategoria() != null) {
            serie.setCategoria(toCategoria(dto.getCategoria()));
        }
        return serie;
    }

    public FacturaDTO toFacturaDTO(Factura factura) {
        if (factura == null) {
            return null;
        }
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setMes(factura.getMes());
        dto.setAño(factura.getAño());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setImporteTotal(factura.getImporteTotal());
        
        if (factura.getUsuario() != null) {
            // Only set login to avoid infinite recursion
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setLogin(factura.getUsuario().getLogin());
            dto.setUsuario(usuarioDTO);
        }
        
        return dto;
    }

    public Factura toFactura(FacturaDTO dto) {
        if (dto == null) {
            return null;
        }
        Factura factura = new Factura();
        factura.setId(dto.getId());
        factura.setMes(dto.getMes());
        factura.setAño(dto.getAño());
        factura.setFechaEmision(dto.getFechaEmision());
        factura.setImporteTotal(dto.getImporteTotal());
        return factura;
    }

    public PersonaDTO toPersonaDTO(Persona persona) {
        if (persona == null) {
            return null;
        }
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        return dto;
    }

    public Persona toPersona(PersonaDTO dto) {
        if (dto == null) {
            return null;
        }
        Persona persona = new Persona();
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        return persona;
    }

    public TipoSuscripcionDTO toTipoSuscripcionDTO(TipoSuscripcion suscripcion) {
        if (suscripcion == null) {
            return null;
        }
        TipoSuscripcionDTO dto = new TipoSuscripcionDTO();
        dto.setId(suscripcion.getId());
        dto.setTipo(suscripcion.getClass().getSimpleName());
        dto.setCostoMensual(suscripcion.calcularCostoMensual());
        return dto;
    }

    public CategoriaDTO toCategoriaDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return CategoriaDTO.valueOf(categoria.name());
    }

    public Categoria toCategoria(CategoriaDTO dto) {
        if (dto == null) {
            return null;
        }
        return Categoria.valueOf(dto.name());
    }

    public TemporadaDTO toTemporadaDTO(Temporada temporada) {
        if (temporada == null) {
            return null;
        }
        TemporadaDTO dto = new TemporadaDTO();
        dto.setId(temporada.getId());
        dto.setNumTemporada(temporada.getNumTemporada());
        
        if (temporada.getSerie() != null) {
            SerieDTO serieDTO = new SerieDTO();
            serieDTO.setId(temporada.getSerie().getId());
            serieDTO.setTitulo(temporada.getSerie().getTitulo());
            dto.setSerie(serieDTO);
        }
        
        if (temporada.getCapitulos() != null) {
            dto.setCapitulos(temporada.getCapitulos().stream()
                    .map(this::toCapituloDTO)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }

    public Temporada toTemporada(TemporadaDTO dto) {
        if (dto == null) {
            return null;
        }
        Temporada temporada = new Temporada();
        temporada.setId(dto.getId());
        temporada.setNumTemporada(dto.getNumTemporada());
        return temporada;
    }

    public CapituloDTO toCapituloDTO(Capitulo capitulo) {
        if (capitulo == null) {
            return null;
        }
        CapituloDTO dto = new CapituloDTO();
        dto.setId(capitulo.getId());
        dto.setTitulo(capitulo.getTitulo());
        dto.setNumCapitulo(capitulo.getNumCapitulo());
        dto.setDescripcion(capitulo.getDescripcion());
        dto.setEnlace(capitulo.getEnlace());
        
        if (capitulo.getTemporada() != null) {
            TemporadaDTO temporadaDTO = new TemporadaDTO();
            temporadaDTO.setId(capitulo.getTemporada().getId());
            temporadaDTO.setNumTemporada(capitulo.getTemporada().getNumTemporada());
            dto.setTemporada(temporadaDTO);
        }
        
        return dto;
    }

    public Capitulo toCapitulo(CapituloDTO dto) {
        if (dto == null) {
            return null;
        }
        Capitulo capitulo = new Capitulo();
        capitulo.setId(dto.getId());
        capitulo.setTitulo(dto.getTitulo());
        capitulo.setNumCapitulo(dto.getNumCapitulo());
        capitulo.setDescripcion(dto.getDescripcion());
        capitulo.setEnlace(dto.getEnlace());
        return capitulo;
    }

    public VisualizacionDTO toVisualizacionDTO(Visualizacion visualizacion) {
        if (visualizacion == null) {
            return null;
        }
        VisualizacionDTO dto = new VisualizacionDTO();
        dto.setId(visualizacion.getId());
        dto.setFechaVisualizacion(visualizacion.getFechaVisualizacion());
        dto.setMinutoActual(visualizacion.getMinutoActual());
        
        if (visualizacion.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setLogin(visualizacion.getUsuario().getLogin());
            dto.setUsuario(usuarioDTO);
        }
        
        if (visualizacion.getCapitulo() != null) {
            dto.setCapitulo(toCapituloDTO(visualizacion.getCapitulo()));
        }
        
        return dto;
    }

    public Visualizacion toVisualizacion(VisualizacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Visualizacion visualizacion = new Visualizacion();
        visualizacion.setId(dto.getId());
        visualizacion.setFechaVisualizacion(dto.getFechaVisualizacion());
        visualizacion.setMinutoActual(dto.getMinutoActual());
        return visualizacion;
    }
}
