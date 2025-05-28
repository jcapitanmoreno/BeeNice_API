package com.github.jcapitanmoreno.beenice_api.services;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Gasto;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.GastoRespository;
import com.github.jcapitanmoreno.beenice_api.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    @Autowired
    private GastoRespository gastoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Obtiene todos los gastos registrados.
     * @return Lista de objetos Gasto.
     */
    public List<Gasto> getAllGasto() {
        List<Gasto> gastos = gastoRepository.findAll();
        if (!gastos.isEmpty()) {
            return gastos;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Crea un nuevo gasto asociado a un grupo.
     * @param grupoId ID del grupo al que se asociará el gasto.
     * @param gasto Objeto Gasto con los detalles del gasto.
     * @return El objeto Gasto creado y guardado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public Gasto createGasto(Long grupoId, Gasto gasto) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            gasto.setIdGrupo(grupo);
            gasto.setPagado(BigDecimal.ZERO);
            gasto.setPendiente(gasto.getTotal());
            gasto.setDescripcion(gasto.getDescripcion());
            return gastoRepository.save(gasto);
        } else {
            throw new RecordNotFoundException("No se encontró el grupo con el ID proporcionado", grupoId);
        }
    }

    /**
     * Obtiene un gasto específico por su ID.
     * @param gastoId ID del gasto que se desea obtener.
     * @return El objeto Gasto correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    public Gasto getGastoById(Long gastoId) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            return gastoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }

    /**
     * Actualiza los detalles de un gasto específico.
     * @param gastoId ID del gasto que se desea actualizar.
     * @param gastoDetails Objeto Gasto con los nuevos detalles.
     * @return El objeto Gasto actualizado.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    public Gasto updateGasto(Long gastoId, Gasto gastoDetails) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            Gasto gasto = gastoOptional.get();


            BigDecimal total = gastoDetails.getTotal() != null ? gastoDetails.getTotal() : BigDecimal.ZERO;
            BigDecimal pagado = gastoDetails.getPagado() != null ? gastoDetails.getPagado() : BigDecimal.ZERO;

            gasto.setTotal(total);
            gasto.setPagado(pagado);
            gasto.setPendiente(total.subtract(pagado));
            gasto.setDescripcion(gastoDetails.getDescripcion());


            return gastoRepository.save(gasto);
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }

    /**
     * Elimina un gasto específico por su ID.
     * @param gastoId ID del gasto que se desea eliminar.
     * @throws RecordNotFoundException Si el gasto no existe.
     */
    public void deleteGasto(Long gastoId) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            gastoRepository.deleteById(gastoId);
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }

    /**
     * Obtiene todos los gastos asociados a un grupo específico.
     * @param grupoId ID del grupo del cual se quieren obtener los gastos.
     * @return Lista de objetos Gasto pertenecientes al grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public List<Gasto> getGastosByGrupoId(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            return grupo.getGastos().stream().toList();
        } else {
            throw new RecordNotFoundException("No se encontró el grupo con el ID proporcionado", grupoId);
        }
    }
}
