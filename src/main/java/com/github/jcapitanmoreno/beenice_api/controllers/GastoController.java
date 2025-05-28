package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Gasto;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
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

    /**
     * Obtiene todos los gastos registrados en el sistema.
     * @return Lista de objetos Gasto.
     */
    @GetMapping
    public ResponseEntity<List<Gasto>> getAllGastos() {
        List<Gasto> usuarios = gastoService.getAllGasto();
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Crea un nuevo gasto asociado a un grupo específico.
     * @param grupoId ID del grupo al que se asociará el gasto.
     * @param gasto Objeto Gasto con los detalles del gasto.
     * @return El objeto Gasto creado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @PostMapping("/{grupoId}")
    public ResponseEntity<Gasto> createGasto(@PathVariable Long grupoId, @RequestBody Gasto gasto) throws RecordNotFoundException {
        Gasto createdGasto = gastoService.createGasto(grupoId, gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGasto);
    }

    /**
     * Obtiene un gasto específico por su ID.
     * @param gastoId ID del gasto que se desea obtener.
     * @return El objeto Gasto correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    @GetMapping("/{gastoId}")
    public ResponseEntity<Gasto> getGastoById(@PathVariable Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);
        return new ResponseEntity<>(gasto, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Actualiza los detalles de un gasto existente.
     * @param gastoId ID del gasto que se desea actualizar.
     * @param gastoDetails Objeto Gasto con los nuevos detalles.
     * @return El objeto Gasto actualizado.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    @PutMapping("/{gastoId}")
    public ResponseEntity<Gasto> updateGasto(@PathVariable Long gastoId, @RequestBody Gasto gastoDetails) throws RecordNotFoundException {
        Gasto updatedGasto = gastoService.updateGasto(gastoId, gastoDetails);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGasto);
    }
    /**
     * Elimina un gasto específico por su ID.
     * @param gastoId ID del gasto que se desea eliminar.
     * @return Estado HTTP indicando el resultado de la operación.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    @DeleteMapping("/{gastoId}")
    public HttpStatus deleteGasto(@PathVariable Long gastoId) throws RecordNotFoundException {
        gastoService.deleteGasto(gastoId);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Obtiene todos los gastos asociados a un grupo específico.
     * @param grupoId ID del grupo del cual se quieren obtener los gastos.
     * @return Lista de objetos Gasto asociados al grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Gasto>> getGastosByGrupoId(@PathVariable Long grupoId) throws RecordNotFoundException {
        List<Gasto> gastos = gastoService.getGastosByGrupoId(grupoId);
        return new ResponseEntity<>(gastos, new HttpHeaders(), HttpStatus.OK);
    }
}