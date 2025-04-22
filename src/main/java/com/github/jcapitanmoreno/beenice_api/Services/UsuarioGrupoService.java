package com.github.jcapitanmoreno.beenice_api.Services;

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


    public Set<Grupo> getGruposByUsuarioId(Long usuarioId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get().getGrupos();
        } else {
            throw new RecordNotFoundException("Usuario no encontrado", usuarioId);
        }
    }


    public Set<Usuario> getUsuariosByGrupoId(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return grupoOptional.get().getUsuarios();
        } else {
            throw new RecordNotFoundException("Grupo no encontrado", grupoId);
        }
    }

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
