package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.dto.*;
import es.unican.gpm178.polaflix.model.Factura;
import es.unican.gpm178.polaflix.service.FacturaService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private DTOMapper dtoMapper;

    @GetMapping
    @JsonView(Vistas.FacturaResumen.class)
    public ResponseEntity<List<FacturaDTO>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        List<FacturaDTO> facturasDTO = facturas.stream()
                .map(this::toResumenDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(facturasDTO);
    }

    @GetMapping("/{id}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> obtenerFacturaPorId(@PathVariable int id) {
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        return factura.map(f -> ResponseEntity.ok(toCompletoDTO(f)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> crearFactura(@Valid @RequestBody FacturaDTO facturaDTO) {
        Factura factura = dtoMapper.toFactura(facturaDTO);
        Factura nuevaFactura = facturaService.crearFactura(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCompletoDTO(nuevaFactura));
    }

    @PutMapping("/{id}")
    @JsonView(Vistas.FacturaCompleto.class)
    public ResponseEntity<FacturaDTO> actualizarFactura(@PathVariable int id, @Valid @RequestBody FacturaDTO facturaDTO) {
        Factura factura = dtoMapper.toFactura(facturaDTO);
        Optional<Factura> facturaActualizada = facturaService.actualizarFactura(id, factura);
        return facturaActualizada.map(f -> ResponseEntity.ok(toCompletoDTO(f)))
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable int id) {
        if (facturaService.eliminarFactura(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{login}")
    @JsonView(Vistas.FacturaResumen.class)
    public ResponseEntity<List<FacturaDTO>> obtenerFacturasPorUsuario(@PathVariable String login) {
        Optional<List<Factura>> facturas = facturaService.obtenerFacturasPorUsuario(login);
        return facturas.map(f -> ResponseEntity.ok(f.stream().map(this::toResumenDTO).collect(Collectors.toList())))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Métodos privados para convertir a DTO
    private FacturaDTO toResumenDTO(Factura factura) {
        if (factura == null) {
            return null;
        }
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setMes(factura.getMes());
        dto.setAño(factura.getAño());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setImporteTotal(factura.getImporteTotal());
        return dto;
    }

    private FacturaDTO toCompletoDTO(Factura factura) {
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
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setLogin(factura.getUsuario().getLogin());
            dto.setUsuario(usuarioDTO);
        }
        
        return dto;
    }
}
