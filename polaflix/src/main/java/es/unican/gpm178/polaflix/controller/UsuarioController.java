package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.dto.*;
import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.model.Usuario;
import es.unican.gpm178.polaflix.service.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(this::toCompletoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{login}")
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorLogin(@PathVariable String login) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorLogin(login);
        return usuario.map(u -> ResponseEntity.ok(toCompletoDTO(u)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = dtoMapper.toUsuario(usuarioDTO);
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCompletoDTO(nuevoUsuario));
    }

    @PutMapping("/{login}")
    @JsonView(Vistas.UsuarioCompleto.class)
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable String login, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = dtoMapper.toUsuario(usuarioDTO);
        Optional<Usuario> usuarioActualizado = usuarioService.actualizarUsuario(login, usuario);
        return usuarioActualizado.map(u -> ResponseEntity.ok(toCompletoDTO(u)))
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
    @JsonView(Vistas.SerieResumen.class)
    public ResponseEntity<Set<SerieDTO>> obtenerSeriesPendientes(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesPendientes(login);
        return series.map(s -> ResponseEntity.ok(s.stream().map(dtoMapper::toSerieDTO).collect(Collectors.toSet())))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{login}/series-empezadas")
    @JsonView(Vistas.SerieResumen.class)
    public ResponseEntity<Set<SerieDTO>> obtenerSeriesEmpezadas(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesEmpezadas(login);
        return series.map(s -> ResponseEntity.ok(s.stream().map(dtoMapper::toSerieDTO).collect(Collectors.toSet())))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{login}/series-terminadas")
    @JsonView(Vistas.SerieResumen.class)
    public ResponseEntity<Set<SerieDTO>> obtenerSeriesTerminadas(@PathVariable String login) {
        Optional<Set<Serie>> series = usuarioService.obtenerSeriesTerminadas(login);
        return series.map(s -> ResponseEntity.ok(s.stream().map(dtoMapper::toSerieDTO).collect(Collectors.toSet())))
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Métodos privados para convertir a DTO
    private UsuarioDTO toResumenDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setLogin(usuario.getLogin());
        return dto;
    }

    private UsuarioDTO toCompletoDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setLogin(usuario.getLogin());
        dto.setPassword(usuario.getPassword());
        dto.setIban(usuario.getIban());
        
        if (usuario.getPlan() != null) {
            dto.setPlan(dtoMapper.toTipoSuscripcionDTO(usuario.getPlan()));
        }
        
        return dto;
    }
}
