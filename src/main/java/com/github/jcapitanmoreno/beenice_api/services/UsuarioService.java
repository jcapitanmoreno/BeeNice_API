package com.github.jcapitanmoreno.beenice_api.services;

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

    public Usuario getUsuarioById(Long id) throws RecordNotFoundException {
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

    public Usuario updateUsuario(Usuario usuario) throws RecordNotFoundException {
        if (usuario.getId() == null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
                usuarioExistente.setContrasena(usuario.getContrasena());
                return usuarioRepository.save(usuarioExistente);
            } else {
                throw new RecordNotFoundException("No existe usuario para el id ", usuario.getId());
            }
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", usuario.getId());
        }
    }

    public void deleteUsuario(Long id) throws RecordNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }


    public List<Usuario> findUsuariosByNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }


    public Optional<Usuario> findUsuarioByCorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }


    public boolean validateUsuario(String correoElectronico, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        return usuario.isPresent() && usuario.get().getContrasena().equals(contrasena);
    }


    public Usuario changePassword(Long id, String nuevaContrasena) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setContrasena(nuevaContrasena);
            return usuarioRepository.save(usuario);
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }


}
