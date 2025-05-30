package com.github.jcapitanmoreno.beenice_api.services;


import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Gasto;
import com.github.jcapitanmoreno.beenice_api.models.Pago;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.PagoRespository;
import com.github.jcapitanmoreno.beenice_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PagoService {

    @Autowired
    private PagoRespository pagoRepository;

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo pago asociado a un usuario y un gasto.
     * @param usuarioId ID del usuario que realiza el pago.
     * @param gastoId ID del gasto al que se asocia el pago.
     * @param pago Objeto Pago con los detalles del pago.
     * @return El objeto Pago creado y guardado.
     * @throws RecordNotFoundException Si el usuario o el gasto no existen.
     */
    public Pago createPago(Long usuarioId, Long gastoId, Pago pago) throws RecordNotFoundException {
        // Validar que el usuario existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RecordNotFoundException("No se encontró el usuario con el ID proporcionado", usuarioId);
        }

        // Validar que el gasto existe
        Gasto gasto = gastoService.getGastoById(gastoId);

        // Asignar el usuario y el gasto al pago
        Usuario usuario = usuarioOptional.get();
        pago.setIdUsuario(usuario);
        pago.setIdGasto(gasto);

        // Validar y asignar el valor de pagadoHastaAhora
        if (pago.getPagadoHastaAhora() == null) {
            pago.setPagadoHastaAhora(BigDecimal.ZERO);
        }

        // Guardar el nuevo pago
        Pago nuevoPago = pagoRepository.save(pago);

        // Actualizar el gasto asociado
        actualizarGastoConPagos(gastoId);

        return nuevoPago;
    }

    /**
     * Obtiene todos los pagos registrados.
     * @return Lista de objetos Pago.
     */
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    /**
     * Obtiene un pago específico por su ID.
     * @param pagoId ID del pago que se desea obtener.
     * @return El objeto Pago correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    public Pago getPagoById(Long pagoId) throws RecordNotFoundException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoId);
        if (pagoOptional.isPresent()) {
            return pagoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró el pago con el ID proporcionado", pagoId);
        }
    }

    /**
     * Actualiza los detalles de un pago específico.
     * @param pagoId ID del pago que se desea actualizar.
     * @param pagoDetails Objeto Pago con los nuevos detalles.
     * @return El objeto Pago actualizado.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    public Pago updatePago(Long pagoId, Pago pagoDetails) throws RecordNotFoundException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoId);
        if (pagoOptional.isPresent()) {
            Pago pago = pagoOptional.get();
            pago.setPagadoHastaAhora(pagoDetails.getPagadoHastaAhora());
            pago.setDescripcionPago(pagoDetails.getDescripcionPago());
            pago.setTotalAPagar(pagoDetails.getTotalAPagar());
            Pago updatedPago = pagoRepository.save(pago);

            // Actualizar el gasto asociado
            actualizarGastoConPagos(pago.getIdGasto().getId());

            return updatedPago;
        } else {
            throw new RecordNotFoundException("No se encontró el pago con el ID proporcionado", pagoId);
        }
    }
    /**
     * Elimina un pago específico por su ID.
     * @param pagoId ID del pago que se desea eliminar.
     * @throws RecordNotFoundException Si el pago no existe.
     */
    public void deletePago(Long pagoId) throws RecordNotFoundException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoId);
        if (pagoOptional.isPresent()) {
            Pago pago = pagoOptional.get();
            pagoRepository.deleteById(pagoId);

            // Actualizar el gasto asociado
            actualizarGastoConPagos(pago.getIdGasto().getId());
        } else {
            throw new RecordNotFoundException("No se encontró el pago con el ID proporcionado", pagoId);
        }
    }

    /**
     * Obtiene todos los pagos asociados a un gasto específico.
     * @param gastoId ID del gasto del cual se quieren obtener los pagos.
     * @return Lista de objetos Pago pertenecientes al gasto.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    public List<Pago> getPagosByGastoId(Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);
        List<Pago> pagos = new ArrayList<>(gasto.getPagos());

        pagos.forEach(pago -> {
            Usuario usuario = usuarioRepository.findById(pago.getIdUsuario().getId()).orElse(null);
            if (usuario != null) {
                pago.getIdUsuario().setNombre(usuario.getNombre());
            }
        });

        return pagos;
    }
    /**
     * Elimina todos los pagos asociados a un gasto específico.
     * @param gastoId ID del gasto del cual se quieren eliminar los pagos.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    public void deletePagosByGastoId(Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);
        List<Pago> pagos = new ArrayList<>(gasto.getPagos());

        if (!pagos.isEmpty()) {
            pagos.forEach(pago -> pagoRepository.deleteById(pago.getId()));
            gasto.setPagado(BigDecimal.ZERO);
            gasto.setPendiente(gasto.getTotal());
            gastoService.updateGasto(gasto.getId(), gasto);
        }
    }

    /**
     * Actualiza los valores de "pagado" y "pendiente" de un gasto específico
     * basado en los pagos asociados.
     * @param gastoId ID del gasto que se desea actualizar.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    private void actualizarGastoConPagos(Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);


        BigDecimal totalPagado = gasto.getPagos().stream()
                .map(pago -> pago.getPagadoHastaAhora() != null ? pago.getPagadoHastaAhora() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        gasto.setPagado(totalPagado);
        gasto.setPendiente(gasto.getTotal().subtract(totalPagado));


        gastoService.updateGasto(gasto.getId(), gasto);
    }

    /**
     * Obtiene todos los pagos realizados por un usuario específico.
     * @param usuarioId ID del usuario del cual se quieren obtener los pagos.
     * @return Lista de objetos Pago realizados por el usuario.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public List<Pago> getPagosByUsuarioId(Long usuarioId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            return pagoRepository.findByIdUsuarioId(usuarioId);
        } else {
            throw new RecordNotFoundException("No se encontró el usuario con el ID proporcionado", usuarioId);
        }
    }
}

