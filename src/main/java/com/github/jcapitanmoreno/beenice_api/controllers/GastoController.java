package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Gasto;
import com.github.jcapitanmoreno.beenice_api.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gasto")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @PostMapping("/{grupoId}")
    public ResponseEntity<Gasto> createGasto(@PathVariable Long grupoId, @RequestBody Gasto gasto) throws RecordNotFoundException {
        Gasto createdGasto = gastoService.createGasto(grupoId, gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGasto);
    }

    @GetMapping("/{gastoId}")
    public ResponseEntity<Gasto> getGastoById(@PathVariable Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);
        return new ResponseEntity<>(gasto, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{gastoId}")
    public ResponseEntity<Gasto> updateGasto(@PathVariable Long gastoId, @RequestBody Gasto gastoDetails) throws RecordNotFoundException {
        Gasto updatedGasto = gastoService.updateGasto(gastoId, gastoDetails);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGasto);
    }

    @DeleteMapping("/{gastoId}")
    public HttpStatus deleteGasto(@PathVariable Long gastoId) throws RecordNotFoundException {
        gastoService.deleteGasto(gastoId);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Gasto>> getGastosByGrupoId(@PathVariable Long grupoId) throws RecordNotFoundException {
        List<Gasto> gastos = gastoService.getGastosByGrupoId(grupoId);
        return new ResponseEntity<>(gastos, new HttpHeaders(), HttpStatus.OK);
    }
}