package es.unican.gpm178.polaflix.service;

import es.unican.gpm178.polaflix.model.Factura;
import es.unican.gpm178.polaflix.repository.FacturaRepository;
import es.unican.gpm178.polaflix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerFacturaPorId(int id) {
        return facturaRepository.findById(id);
    }

    public Factura crearFactura(Factura factura) {
        validarFactura(factura);
        return facturaRepository.save(factura);
    }

    public Optional<Factura> actualizarFactura(int id, Factura facturaActualizada) {
        return facturaRepository.findById(id).map(factura -> {
            if (facturaActualizada.getMes() > 0 && facturaActualizada.getMes() <= 12) {
                factura.setMes(facturaActualizada.getMes());
            }
            if (facturaActualizada.getAño() > 0) {
                factura.setAño(facturaActualizada.getAño());
            }
            if (facturaActualizada.getFechaEmision() != null) {
                factura.setFechaEmision(facturaActualizada.getFechaEmision());
            }
            if (facturaActualizada.getImporteTotal() >= 0) {
                factura.setImporteTotal(facturaActualizada.getImporteTotal());
            }
            return facturaRepository.save(factura);
        });
    }

    public boolean eliminarFactura(int id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<List<Factura>> obtenerFacturasPorUsuario(String login) {
        return usuarioRepository.findById(login).map(usuario -> 
            facturaRepository.findAll().stream()
                .filter(f -> f.getUsuario() != null && f.getUsuario().getLogin().equals(login))
                .collect(Collectors.toList())
        );
    }

    private void validarFactura(Factura factura) {
        if (factura.getMes() <= 0 || factura.getMes() > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (factura.getAño() <= 0) {
            throw new IllegalArgumentException("El año debe ser mayor a 0");
        }
        if (factura.getUsuario() == null) {
            throw new IllegalArgumentException("La factura debe estar asociada a un usuario");
        }
    }
}
