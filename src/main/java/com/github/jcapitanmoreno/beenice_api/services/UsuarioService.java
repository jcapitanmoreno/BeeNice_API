package com.github.jcapitanmoreno.beenice_api.services;

import com.github.jcapitanmoreno.beenice_api.config.PasswordUtil;
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

    /**
     * Obtiene todos los usuarios registrados.
     * @return Lista de objetos Usuario. Si no hay usuarios, devuelve una lista vacía.
     */
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            return usuarios;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public Usuario getUsuarioById(Long id) throws RecordNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }

    /**
     * Crea un nuevo usuario con su contraseña encriptada.
     * @param usuario Objeto Usuario con los detalles del usuario.
     * @return El objeto Usuario creado y guardado.
     */
    public Usuario createUsuario(Usuario usuario) {
        usuario.setContrasena(PasswordUtil.hashPassword(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los detalles de un usuario existente.
     * @param usuario Objeto Usuario con los nuevos detalles.
     * @return El objeto Usuario actualizado.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public Usuario updateUsuario(Usuario usuario) throws RecordNotFoundException {
        if (usuario.getId() == null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
                if (usuario.getContrasena() != null) {
                    usuarioExistente.setContrasena(PasswordUtil.hashPassword(usuario.getContrasena()));
                }
                return usuarioRepository.save(usuarioExistente);
            } else {
                throw new RecordNotFoundException("No existe usuario para el id ", usuario.getId());
            }
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", usuario.getId());
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario que se desea eliminar.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public void deleteUsuario(Long id) throws RecordNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }

    /**
     * Busca usuarios por su nombre, ignorando mayúsculas y minúsculas.
     * @param nombre Nombre o parte del nombre del usuario que se desea buscar.
     * @return Lista de objetos Usuario que coinciden con el nombre proporcionado.
     */
    public List<Usuario> findUsuariosByNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @param correoElectronico Correo electrónico del usuario que se desea buscar.
     * @return Un objeto Optional que contiene el Usuario si se encuentra.
     */
    public Optional<Usuario> findUsuarioByCorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }

    /**
     * Valida las credenciales de un usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean validateUsuario(String correoElectronico, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        return usuario.isPresent() && PasswordUtil.checkPassword(contrasena, usuario.get().getContrasena());
    }

    /**
     * Cambia la contraseña de un usuario.
     * @param id ID del usuario cuya contraseña se desea cambiar.
     * @param nuevaContrasena Nueva contraseña que se asignará al usuario.
     * @return El objeto Usuario actualizado con la nueva contraseña.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    public Usuario changePassword(Long id, String nuevaContrasena) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setContrasena(PasswordUtil.hashPassword(nuevaContrasena));
            return usuarioRepository.save(usuario);
        } else {
            throw new RecordNotFoundException("No existe usuario para el id ", id);
        }
    }


}
