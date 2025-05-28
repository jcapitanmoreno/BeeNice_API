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

    /**
     * Crea un nuevo pago asociado a un usuario y un gasto específicos.
     * @param usuarioId ID del usuario que realiza el pago.
     * @param gastoId ID del gasto al que se asocia el pago.
     * @param pago Objeto Pago con los detalles del pago.
     * @return El objeto Pago creado.
     * @throws RecordNotFoundException Si el usuario o el gasto no existen.
     */
    @PostMapping("/{usuarioId}/{gastoId}")
    public ResponseEntity<Pago> createPago(@PathVariable Long usuarioId, @PathVariable Long gastoId, @RequestBody Pago pago) throws RecordNotFoundException {
        Pago createdPago = pagoService.createPago(usuarioId, gastoId, pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPago);
    }

    /**
     * Obtiene todos los pagos registrados en el sistema.
     * @return Lista de objetos Pago.
     */
    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        List<Pago> pagos = pagoService.getAllPagos();
        return new ResponseEntity<>(pagos, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene un pago específico por su ID.
     * @param pagoId ID del pago que se desea obtener.
     * @return El objeto Pago correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    @GetMapping("/{pagoId}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long pagoId) throws RecordNotFoundException {
        Pago pago = pagoService.getPagoById(pagoId);
        return new ResponseEntity<>(pago, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Actualiza los detalles de un pago existente.
     * @param pagoId ID del pago que se desea actualizar.
     * @param pagoDetails Objeto Pago con los nuevos detalles.
     * @return El objeto Pago actualizado.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    @PutMapping("/{pagoId}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long pagoId, @RequestBody Pago pagoDetails) throws RecordNotFoundException {
        Pago updatedPago = pagoService.updatePago(pagoId, pagoDetails);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPago);
    }

    /**
     * Elimina un pago específico por su ID.
     * @param pagoId ID del pago que se desea eliminar.
     * @return Estado HTTP indicando el resultado de la operación.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    @DeleteMapping("/{pagoId}")
    public HttpStatus deletePago(@PathVariable Long pagoId) throws RecordNotFoundException {
        pagoService.deletePago(pagoId);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Elimina todos los pagos asociados a un gasto específico.
     * @param gastoId ID del gasto cuyos pagos se desean eliminar.
     * @return Estado HTTP indicando el resultado de la operación.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    @DeleteMapping("/gasto/{gastoId}")
    public HttpStatus deletePagosByGastoId(@PathVariable Long gastoId) throws RecordNotFoundException {
        pagoService.deletePagosByGastoId(gastoId);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Obtiene todos los pagos asociados a un gasto específico.
     * @param gastoId ID del gasto cuyos pagos se desean obtener.
     * @return Lista de objetos Pago asociados al gasto.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    @GetMapping("/gasto/{gastoId}")
    public ResponseEntity<List<Pago>> getPagosByGastoId(@PathVariable Long gastoId) throws RecordNotFoundException {
        List<Pago> pagos = pagoService.getPagosByGastoId(gastoId);
        return new ResponseEntity<>(pagos, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene todos los pagos realizados por un usuario específico.
     * @param usuarioId ID del usuario cuyos pagos se desean obtener.
     * @return Lista de objetos Pago realizados por el usuario.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pago>> getPagosByUsuarioId(@PathVariable Long usuarioId) throws RecordNotFoundException {
        List<Pago> pagos = pagoService.getPagosByUsuarioId(usuarioId);
        return new ResponseEntity<>(pagos, new HttpHeaders(), HttpStatus.OK);
    }
}