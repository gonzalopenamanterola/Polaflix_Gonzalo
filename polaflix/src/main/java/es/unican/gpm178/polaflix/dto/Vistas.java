package es.unican.gpm178.polaflix.dto;

public final class Vistas {

    private Vistas() {}

    public interface Public {}

    public interface UsuarioResumen extends Public {}
    public interface UsuarioCompleto extends UsuarioResumen {}

    public interface SerieResumen extends Public {}
    public interface SerieCompleto extends SerieResumen {}

    public interface FacturaResumen extends Public {}
    public interface FacturaCompleto extends FacturaResumen, UsuarioResumen {}

    public interface PersonaResumen extends Public {}
    public interface PersonaCompleto extends PersonaResumen {}

    public interface TipoSuscripcionResumen extends Public {}
    public interface TipoSuscripcionCompleto extends TipoSuscripcionResumen {}

    public interface TemporadaResumen extends Public {}
    public interface TemporadaCompleto extends TemporadaResumen {}

    public interface CapituloResumen extends Public {}
    public interface CapituloCompleto extends CapituloResumen {}

    public interface VisualizacionResumen extends Public {}
    public interface VisualizacionCompleto extends VisualizacionResumen {}
}
