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
        feedPersonas();
        feedSeries();
        feedUsuarios();
        feedFacturas();
        testDatabase();
        System.out.println("Base de datos inicializada con datos de ejemplo");
    }

    private void feedPersonas() {
        Persona persona1 = new Persona(null, "Juan", "Pérez");
        Persona persona2 = new Persona(null, "María", "García");
        personaRepository.saveAll(Arrays.asList(persona1, persona2));
    }

    private void feedSeries() {
        Serie serie1 = new Serie(
                1,
                "Breaking Bad",
                "Un profesor de química se adentra en el mundo de la metanfetamina",
                'B',
                Arrays.asList("Vince Gilligan"),
                Arrays.asList("Bryan Cranston", "Aaron Paul"),
                Categoria.ESTANDAR,
                null
        );

        Serie serie2 = new Serie(
                2,
                "Stranger Things",
                "Niños y fenómenos paranormales en un pequeño pueblo de Estados Unidos",
                'S',
                Arrays.asList("The Duffer Brothers"),
                Arrays.asList("Millie Bobby Brown", "Finn Wolfhard"),
                Categoria.SILVER,
                null
        );

        serieRepository.saveAll(Arrays.asList(serie1, serie2));
    }

    private void feedUsuarios() {
        Usuario usuario1 = new Usuario(
                "user1",
                "password1",
                "ES1234567890123456789012",
                null,
                null,
                null,
                null,
                null
        );

        Usuario usuario2 = new Usuario(
                "user2",
                "password2",
                "ES9876543210987654321098",
                null,
                null,
                null,
                null,
                null
        );

        usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
    }

    private void feedFacturas() {
        Usuario usuario1 = usuarioRepository.findById("user1").orElse(null);
        Usuario usuario2 = usuarioRepository.findById("user2").orElse(null);

        if (usuario1 == null || usuario2 == null) {
            System.out.println("No se pudieron crear facturas porque faltan usuarios");
            return;
        }

        Factura factura1 = new Factura(0, 3, 2026, new Date(), 15.99, null, usuario1);
        Factura factura2 = new Factura(0, 3, 2026, new Date(), 12.99, null, usuario2);

        facturaRepository.saveAll(Arrays.asList(factura1, factura2));
    }

    private void testDatabase() {
        long usuarios = usuarioRepository.count();
        long series = serieRepository.count();
        long facturas = facturaRepository.count();
        long personas = personaRepository.count();

        System.out.println("Usuarios cargados = " + usuarios);
        System.out.println("Series cargadas = " + series);
        System.out.println("Facturas cargadas = " + facturas);
        System.out.println("Personas cargadas = " + personas);

        usuarioRepository.findById("user1").ifPresent(u -> {
            System.out.println("Usuario 'user1' encontrado: " + u.getLogin() + ", IBAN=" + u.getIban());
        });

        serieRepository.findById(1).ifPresent(s -> {
            System.out.println("Serie 'Breaking Bad' encontrada: " + s.getTitulo());
        });
    }
}
