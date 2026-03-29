package es.unican.gpm178.polaflix;

import es.unican.gpm178.polaflix.model.*;
import es.unican.gpm178.polaflix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear personas
        Persona persona1 = new Persona(null, "Juan", "Pérez");
        Persona persona2 = new Persona(null, "María", "García");
        personaRepository.saveAll(Arrays.asList(persona1, persona2));

        // Crear series
        Serie serie1 = new Serie(1, "Breaking Bad", "Una serie sobre un profesor de química que se convierte en fabricante de metanfetaminas",
                'B', Arrays.asList("Vince Gilligan"), Arrays.asList("Bryan Cranston", "Aaron Paul"), Categoria.ESTANDAR, null);
        Serie serie2 = new Serie(2, "Stranger Things", "Una serie de ciencia ficción sobre niños que descubren fenómenos paranormales",
                'S', Arrays.asList("The Duffer Brothers"), Arrays.asList("Millie Bobby Brown", "Finn Wolfhard"), Categoria.SILVER, null);
        serieRepository.saveAll(Arrays.asList(serie1, serie2));

        // Crear usuarios
        Usuario usuario1 = new Usuario("user1", "password1", "ES1234567890123456789012", null, null, null, null, null);
        Usuario usuario2 = new Usuario("user2", "password2", "ES9876543210987654321098", null, null, null, null, null);
        usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));

        // Crear facturas
        Factura factura1 = new Factura(0, 3, 2026, new Date(), 15.99, null, usuario1);
        Factura factura2 = new Factura(0, 3, 2026, new Date(), 12.99, null, usuario2);
        facturaRepository.saveAll(Arrays.asList(factura1, factura2));

        System.out.println("Base de datos inicializada con datos de ejemplo");
    }
}