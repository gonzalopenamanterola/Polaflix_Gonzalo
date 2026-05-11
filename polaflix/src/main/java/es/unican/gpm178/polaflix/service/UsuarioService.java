package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.Usuario;
import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.dto.UsuarioDTO;
import es.unican.gpm178.polaflix.dto.DTOMapper;
import es.unican.gpm178.polaflix.repository.UsuarioRepository;
import es.unican.gpm178.polaflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private DTOMapper dtoMapper;

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorLogin(String login) {
        return usuarioRepository.findById(login);
    }

    public Optional<UsuarioDTO> obtenerUsuarioPorLoginDTO(String login) {
        return usuarioRepository.findById(login).map(this::convertToDTO);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setLogin(usuario.getLogin());
        dto.setPassword(usuario.getPassword());
        dto.setIban(usuario.getIban());
        
        // Load all collections here, within the transaction
        Set<Serie> seriesPendientes = usuario.getSeriesPendientes();
        Set<Serie> seriesEmpezadas = usuario.getSeriesEmpezadas();
        Set<Serie> seriesTerminadas = usuario.getSeriesTerminadas();
        
        // Convert to DTOs
        dto.setSeriesPendientes(seriesPendientes.stream()
                .map(dtoMapper::toSerieDTO)
                .collect(Collectors.toSet()));
        dto.setSeriesEmpezadas(seriesEmpezadas.stream()
                .map(dtoMapper::toSerieDTO)
                .collect(Collectors.toSet()));
        dto.setSeriesTerminadas(seriesTerminadas.stream()
                .map(dtoMapper::toSerieDTO)
                .collect(Collectors.toSet()));
        dto.setFacturas(usuario.getFacturas().stream()
                .map(dtoMapper::toFacturaDTO)
                .collect(Collectors.toSet()));
        
        if (usuario.getPlan() != null) {
            dto.setPlan(dtoMapper.toTipoSuscripcionDTO(usuario.getPlan()));
        }
        
        return dto;
    }

    public Usuario crearUsuario(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(String login, Usuario usuarioActualizado) {
        return usuarioRepository.findById(login).map(usuario -> {
            if (usuarioActualizado.getPassword() != null) {
                usuario.setPassword(usuarioActualizado.getPassword());
            }
            if (usuarioActualizado.getIban() != null) {
                usuario.setIban(usuarioActualizado.getIban());
            }
            if (usuarioActualizado.getPlan() != null) {
                usuario.setPlan(usuarioActualizado.getPlan());
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean agregarSeriePendiente(String login, int serieId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(login);
        Optional<Serie> serieOpt = serieRepository.findById(serieId);

        if (usuarioOpt.isPresent() && serieOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Serie serie = serieOpt.get();
            usuario.agregarSeriePendiente(serie);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Optional<Set<Serie>> obtenerSeriesPendientes(String login) {
        return usuarioRepository.findById(login).map(Usuario::getSeriesPendientes);
    }

    public Optional<Set<Serie>> obtenerSeriesEmpezadas(String login) {
        return usuarioRepository.findById(login).map(Usuario::getSeriesEmpezadas);
    }

    public Optional<Set<Serie>> obtenerSeriesTerminadas(String login) {
        return usuarioRepository.findById(login).map(Usuario::getSeriesTerminadas);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getLogin() == null || usuario.getLogin().isEmpty()) {
            throw new IllegalArgumentException("El login del usuario no puede estar vacío");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña del usuario no puede estar vacía");
        }
        if (usuario.getIban() == null || usuario.getIban().isEmpty()) {
            throw new IllegalArgumentException("El IBAN del usuario no puede estar vacío");
        }
    }
}
