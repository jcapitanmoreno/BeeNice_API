package com.github.jcapitanmoreno.beenice_api.services;

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

    /**
     * Crea un nuevo grupo con un código único.
     * @param grupo Objeto Grupo con los detalles del grupo.
     * @return El objeto Grupo creado y guardado.
     */
    public Grupo createGrupo(Grupo grupo) {
        grupo.setCodigoGrupo(UUID.randomUUID().toString().substring(0, 4));
        return grupoRepository.save(grupo);
    }

    /**
     * Obtiene un grupo por su código único.
     * @param codigoGrupo Código único del grupo.
     * @return El objeto Grupo correspondiente al código proporcionado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public Grupo getGrupoByCodigo(String codigoGrupo) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findByCodigoGrupo(codigoGrupo);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el código proporcionado", codigoGrupo);
        }
    }

    /**
     * Obtiene un grupo por su ID.
     * @param grupoId ID del grupo que se desea obtener.
     * @return El objeto Grupo correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public Grupo getGrupoById(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get();
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

    /**
     * Actualiza los detalles de un grupo específico.
     * @param grupo Objeto Grupo con los nuevos detalles.
     * @return El objeto Grupo actualizado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public Grupo updateGrupo(Grupo grupo) throws RecordNotFoundException {
        if (grupo.getId() != null) { // Verifica que el ID no sea null
            Optional<Grupo> grupoOptional = grupoRepository.findById(grupo.getId());
            if (grupoOptional.isPresent()) {
                Grupo grupoExistente = grupoOptional.get();
                grupoExistente.setDescripcionGeneral(grupo.getDescripcionGeneral());
                grupoExistente.setNota(grupo.getNota());
                grupoExistente.setImagen(grupo.getImagen());
                return grupoRepository.save(grupoExistente);
            } else {
                throw new RecordNotFoundException("No existe grupo para el id ", grupo.getId());
            }
        } else {
            throw new RecordNotFoundException("El ID del grupo no puede ser null", null);
        }
    }

    /**
     * Elimina un grupo específico por su ID.
     * @param grupoId ID del grupo que se desea eliminar.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public void deleteGrupo(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            grupoRepository.deleteById(grupoId);
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

    /**
     * Obtiene todos los grupos registrados.
     * @return Lista de objetos Grupo.
     */
    public List<Grupo> getAllGrupos() {
        return grupoRepository.findAll();
    }

    /**
     * Asigna un creador a un grupo específico.
     * @param grupoId ID del grupo al que se asignará el creador.
     * @param creador Objeto Usuario que será asignado como creador.
     * @return El objeto Grupo actualizado con el creador asignado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
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

    /**
     * Obtiene el código único de un grupo por su ID.
     * @param grupoId ID del grupo.
     * @return Código único del grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public String getCodigoGrupoById(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get().getCodigoGrupo();
        } else {
            throw new RecordNotFoundException("No se encontró un grupo con el ID proporcionado", grupoId);
        }
    }

}
