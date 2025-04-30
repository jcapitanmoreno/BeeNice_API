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

    public List<Gasto> getAllGasto() {
        List<Gasto> gastos = gastoRepository.findAll();
        if (!gastos.isEmpty()) {
            return gastos;
        } else {
            return new ArrayList<>();
        }
    }


    public Gasto createGasto(Long grupoId, Gasto gasto) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            gasto.setIdGrupo(grupo);
            gasto.setPagado(BigDecimal.ZERO);
            gasto.setPendiente(gasto.getTotal());
            return gastoRepository.save(gasto);
        } else {
            throw new RecordNotFoundException("No se encontró el grupo con el ID proporcionado", grupoId);
        }
    }


    public Gasto getGastoById(Long gastoId) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            return gastoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }


    public Gasto updateGasto(Long gastoId, Gasto gastoDetails) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            Gasto gasto = gastoOptional.get();
            gasto.setTotal(gastoDetails.getTotal());
            gasto.setPagado(gastoDetails.getPagado());
            gasto.setPendiente(gastoDetails.getTotal().subtract(gastoDetails.getPagado()));
            return gastoRepository.save(gasto);
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }


    public void deleteGasto(Long gastoId) throws RecordNotFoundException {
        Optional<Gasto> gastoOptional = gastoRepository.findById(gastoId);
        if (gastoOptional.isPresent()) {
            gastoRepository.deleteById(gastoId);
        } else {
            throw new RecordNotFoundException("No se encontró el gasto con el ID proporcionado", gastoId);
        }
    }


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
