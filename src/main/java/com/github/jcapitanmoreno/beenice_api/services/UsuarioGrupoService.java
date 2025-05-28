package com.github.jcapitanmoreno.beenice_api.services;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.GrupoRepository;
import com.github.jcapitanmoreno.beenice_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Agrega un usuario a un grupo específico.
     * @param usuarioId ID del usuario que se desea agregar al grupo.
     * @param grupoId ID del grupo al que se desea agregar el usuario.
     * @return El objeto Grupo actualizado con el usuario agregado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    public Grupo addUsuarioToGrupo(Long usuarioId, Long grupoId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);

        if (usuarioOptional.isPresent() && grupoOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Grupo grupo = grupoOptional.get();

            grupo.getUsuarios().add(usuario);
            usuario.getGrupos().add(grupo);

            grupoRepository.save(grupo);
            usuarioRepository.save(usuario);

            return grupo;
        } else {
            throw new RecordNotFoundException("Usuario o grupo no encontrado", usuarioId);
        }
    }

    /**
     * Elimina un usuario de un grupo específico.
     * @param usuarioId ID del usuario que se desea eliminar del grupo.
     * @param grupoId ID del grupo del que se desea eliminar el usuario.
     * @return El objeto Grupo actualizado con el usuario eliminado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    public Grupo removeUsuarioFromGrupo(Long usuarioId, Long grupoId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);

        if (usuarioOptional.isPresent() && grupoOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Grupo grupo = grupoOptional.get();

            grupo.getUsuarios().remove(usuario);
            usuario.getGrupos().remove(grupo);

            grupoRepository.save(grupo);
            usuarioRepository.save(usuario);

            return grupo;
        } else {
            throw new RecordNotFoundException("Usuario o grupo no encontrado", usuarioId);
        }
    }

    /**
     * Obtiene todos los grupos asociados a un usuario específico.
     * @param usuarioId ID del usuario del cual se quieren obtener los grupos.
     * @return Conjunto de objetos Grupo asociados al usuario.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public Set<Grupo> getGruposByUsuarioId(Long usuarioId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get().getGrupos();
        } else {
            throw new RecordNotFoundException("Usuario no encontrado", usuarioId);
        }
    }

    /**
     * Obtiene todos los usuarios asociados a un grupo específico.
     * @param grupoId ID del grupo del cual se quieren obtener los usuarios.
     * @return Conjunto de objetos Usuario asociados al grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    public Set<Usuario> getUsuariosByGrupoId(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get().getUsuarios();
        } else {
            throw new RecordNotFoundException("Grupo no encontrado", grupoId);
        }
    }
    /**
     * Permite a un usuario unirse a un grupo utilizando su código único.
     * @param usuarioId ID del usuario que se desea unir al grupo.
     * @param codigoGrupo Código único del grupo al que se desea unir el usuario.
     * @return El objeto Grupo actualizado con el usuario agregado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    public Grupo joinGrupoByCodigo(Long usuarioId, String codigoGrupo) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        Optional<Grupo> grupoOptional = grupoRepository.findByCodigoGrupo(codigoGrupo);

        if (usuarioOptional.isPresent() && grupoOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Grupo grupo = grupoOptional.get();

            grupo.getUsuarios().add(usuario);
            usuario.getGrupos().add(grupo);

            grupoRepository.save(grupo);
            usuarioRepository.save(usuario);

            return grupo;
        } else {
            throw new RecordNotFoundException("Usuario o grupo no encontrado", usuarioId);
        }
    }

}
