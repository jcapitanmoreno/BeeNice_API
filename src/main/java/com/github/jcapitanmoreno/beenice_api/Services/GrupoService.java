package com.github.jcapitanmoreno.beenice_api.Services;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;


    public Grupo createGrupo(Grupo grupo) {
        grupo.setCodigoGrupo(UUID.randomUUID().toString().substring(0, 4));
        return grupoRepository.save(grupo);
    }

    public Grupo getGrupoByCodigo(String codigoGrupo) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findByCodigoGrupo(codigoGrupo);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el código proporcionado", codigoGrupo);
        }
    }

    public Grupo getGrupoById(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

    public Grupo updateGrupo(Grupo grupo) throws RecordNotFoundException {
        if (grupo.getId() == null) {
            Optional<Grupo> grupoOptional = grupoRepository.findById(grupo.getId());
            if (grupoOptional.isPresent()) {
                Grupo grupoExistente = grupoOptional.get();
                grupoExistente.setDescripcionGeneral(grupo.getDescripcionGeneral());
                return grupoRepository.save(grupoExistente);
            } else {
                throw new RecordNotFoundException("No existe grupo para el id ", grupo.getId());
            }
        } else {
            throw new RecordNotFoundException("No existe grupo para el id ", grupo.getId());
        }
    }

    public void deleteGrupo(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            grupoRepository.deleteById(grupoId);
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

    public List<Grupo> getAllGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo assignarCreadorToGrupo(Long grupoId, Usuario creador) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            grupo.getUsuarios().add(creador); // Asegúrate de que el creador esté en la lista de usuarios
            return grupoRepository.save(grupo);
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

}
