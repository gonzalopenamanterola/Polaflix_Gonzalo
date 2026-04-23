package es.unican.gpm178.polaflix.controller;

import es.unican.gpm178.polaflix.model.Factura;
import es.unican.gpm178.polaflix.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasLasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable int id) {
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        return factura.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.crearFactura(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable int id, @RequestBody Factura factura) {
        Optional<Factura> facturaActualizada = facturaService.actualizarFactura(id, factura);
        return facturaActualizada.map(ResponseEntity::ok)
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
    public ResponseEntity<List<Factura>> obtenerFacturasPorUsuario(@PathVariable String login) {
        Optional<List<Factura>> facturas = facturaService.obtenerFacturasPorUsuario(login);
        return facturas.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
