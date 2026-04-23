package es.unican.gpm178.polaflix;

import es.unican.gpm178.polaflix.model.*;
import es.unican.gpm178.polaflix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Transactional
    public void run(String... args) throws Exception {
        feedSeries();
        feedPersonasFromSeries();
        feedUsuarios();
        feedFacturas();
        testDatabase();
        System.out.println("Base de datos inicializada con datos de ejemplo");
    }

    private void feedSeries() {
        Serie serie1 = new Serie(
                0,
                "Breaking Bad",
                "Un profesor de química se adentra en el mundo de la metanfetamina",
                'B',
                new HashSet<>(Arrays.asList("Vince Gilligan")),
                new HashSet<>(Arrays.asList("Bryan Cranston", "Aaron Paul")),
                Categoria.ESTANDAR
        );

        Serie serie2 = new Serie(
                0,
                "Stranger Things",
                "Niños y fenómenos paranormales en un pequeño pueblo de Estados Unidos",
                'S',
                new HashSet<>(Arrays.asList("Duffer Brothers")),
                new HashSet<>(Arrays.asList("Millie Bobby Brown", "Finn Wolfhard")),
                Categoria.SILVER
        );

        serieRepository.saveAll(Arrays.asList(serie1, serie2));
    }

    private void feedPersonasFromSeries() {
        Set<String> nombresPersonas = new HashSet<>();
        
        // Extraer todos los creadores y actores de las series
        serieRepository.findAll().forEach(serie -> {
            if (serie.getCreadores() != null) {
                nombresPersonas.addAll(serie.getCreadores());
            }
            if (serie.getActores() != null) {
                nombresPersonas.addAll(serie.getActores());
            }
        });
        

        Set<Persona> personas = new HashSet<>();
        for (String nombre : nombresPersonas) {
            String[] partes = nombre.split("\\s+");
            String nombrePersona = partes[0];
            String apellido = partes.length > 1 ? partes[1] : "";
            
            Persona persona = new Persona();
            persona.setNombre(nombrePersona);
            persona.setApellido(apellido);
            personas.add(persona);
        }
        
        personaRepository.saveAll(personas);
        System.out.println("Personas creadas desde creadores y actores: " + personas.size());
    }

    private void feedUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setLogin("user1");
        usuario1.setPassword("password1");
        usuario1.setIban("ES1234567890123456789012");

        Usuario usuario2 = new Usuario();
        usuario2.setLogin("user2");
        usuario2.setPassword("password2");
        usuario2.setIban("ES9876543210987654321098");

        usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
    }

    private void feedFacturas() {
        Usuario usuario1 = usuarioRepository.findById("user1").orElse(null);
        Usuario usuario2 = usuarioRepository.findById("user2").orElse(null);

        if (usuario1 == null || usuario2 == null) {
            System.out.println("No se pudieron crear facturas porque faltan usuarios");
            return;
        }

        Factura factura1 = new Factura(0, 3, 2026, new Date(), 15.99, usuario1);
        Factura factura2 = new Factura(0, 3, 2026, new Date(), 12.99, usuario2);

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

        serieRepository.findAll().stream()
                .filter(s -> s.getTitulo().equals("Breaking Bad"))
                .findFirst()
                .ifPresent(s -> System.out.println("Serie 'Breaking Bad' encontrada: " + s.getTitulo()));
    }
}
