package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.Usuario;
import es.unican.gpm178.polaflix.model.Serie;
import es.unican.gpm178.polaflix.repository.UsuarioRepository;
import es.unican.gpm178.polaflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorLogin(String login) {
        return usuarioRepository.findById(login);
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
