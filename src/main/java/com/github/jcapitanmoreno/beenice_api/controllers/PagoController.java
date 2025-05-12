package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Pago;
import com.github.jcapitanmoreno.beenice_api.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Pago> createPago(@PathVariable Long usuarioId, @RequestBody Pago pago) throws RecordNotFoundException {
        Pago createdPago = pagoService.createPago(usuarioId, pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPago);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        List<Pago> pagos = pagoService.getAllPagos();
        return new ResponseEntity<>(pagos, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{pagoId}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long pagoId) throws RecordNotFoundException {
        Pago pago = pagoService.getPagoById(pagoId);
        return new ResponseEntity<>(pago, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{pagoId}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long pagoId, @RequestBody Pago pagoDetails) throws RecordNotFoundException {
        Pago updatedPago = pagoService.updatePago(pagoId, pagoDetails);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPago);
    }

    @DeleteMapping("/{pagoId}")
    public HttpStatus deletePago(@PathVariable Long pagoId) throws RecordNotFoundException {
        pagoService.deletePago(pagoId);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/gasto/{gastoId}")
    public HttpStatus deletePagosByGastoId(@PathVariable Long gastoId) throws RecordNotFoundException {
        pagoService.deletePagosByGastoId(gastoId);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/gasto/{gastoId}")
    public ResponseEntity<List<Pago>> getPagosByGastoId(@PathVariable Long gastoId) throws RecordNotFoundException {
        List<Pago> pagos = pagoService.getPagosByGastoId(gastoId);
        return new ResponseEntity<>(pagos, new HttpHeaders(), HttpStatus.OK);
    }
}