package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.model.Usuario;
import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{login}")
    public ResponseEntity<Usuario> obtenerUsuarioPorLogin(@PathVariable String login) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorLogin(login);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @PutMapping("/{login}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String login, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioActualizado = usuarioService.actualizarUsuario(login, usuario);
        return usuarioActualizado.map(ResponseEntity::ok)
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{login}/series-pendientes/{serieId}")
    public ResponseEntity<Void> agregarSeriePendiente(@PathVariable String login, @PathVariable int serieId) {
        if (usuarioService.agregarSeriePendiente(login, serieId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{login}/series-pendientes")
    public ResponseEntity<Set<Serie>> obtenerSeriesPendientes(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesPendientes(login);
        return series.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{login}/series-empezadas")
    public ResponseEntity<Set<Serie>> obtenerSeriesEmpezadas(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesEmpezadas(login);
        return series.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{login}/series-terminadas")
    public ResponseEntity<Set<Serie>> obtenerSeriesTerminadas(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesTerminadas(login);
        return series.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
