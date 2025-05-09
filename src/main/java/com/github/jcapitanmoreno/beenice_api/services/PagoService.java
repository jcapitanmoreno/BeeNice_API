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

    public Pago createPago(Long usuarioId, Pago pago) throws RecordNotFoundException {
        // Validar que el usuario existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RecordNotFoundException("No se encontró el usuario con el ID proporcionado", usuarioId);
        }

        // Asignar el usuario al pago
        Usuario usuario = usuarioOptional.get();
        pago.setIdUsuario(usuario);

        // Validar y asignar el valor de pagadoHastaAhora
        if (pago.getPagadoHastaAhora() == null) {
            pago.setPagadoHastaAhora(BigDecimal.ZERO);
        }

        // Guardar el nuevo pago
        Pago nuevoPago = pagoRepository.save(pago);

        // Actualizar el gasto asociado
        actualizarGastoConPagos(pago.getIdGasto().getId());

        return nuevoPago;
    }

    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    public Pago getPagoById(Long pagoId) throws RecordNotFoundException {
        Optional<Pago> pagoOptional = pagoRepository.findById(pagoId);
        if (pagoOptional.isPresent()) {
            return pagoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró el pago con el ID proporcionado", pagoId);
        }
    }

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

    private void actualizarGastoConPagos(Long gastoId) throws RecordNotFoundException {
        Gasto gasto = gastoService.getGastoById(gastoId);

        // Calcular el total pagado sumando los valores de "pagadoHastaAhora" de los pagos asociados
        BigDecimal totalPagado = gasto.getPagos().stream()
                .map(pago -> pago.getPagadoHastaAhora() != null ? pago.getPagadoHastaAhora() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Actualizar los valores de "pagado" y "pendiente" en el gasto
        gasto.setPagado(totalPagado);
        gasto.setPendiente(gasto.getTotal().subtract(totalPagado));

        // Guardar los cambios en el gasto
        gastoService.updateGasto(gasto.getId(), gasto);
    }

}

