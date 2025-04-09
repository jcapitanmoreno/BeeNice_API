package com.github.jcapitanmoreno.beenice_api.Services;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            return usuarios;
        } else {
            return new ArrayList<>();
        }
    }

    public Usuario getUsuarioById(Long id)  throws RecordNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }

    public Usuario createUsuario(Usuario usuario) {
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }
}
