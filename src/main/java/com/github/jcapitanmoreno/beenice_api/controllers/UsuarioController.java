package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.config.PasswordUtil;
import com.github.jcapitanmoreno.beenice_api.services.UsuarioService;
import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return Lista de objetos Usuario.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene un usuario específico por su ID.
     * @param id ID del usuario que se desea obtener.
     * @return El objeto Usuario correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) throws RecordNotFoundException {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(usuario, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * @param usuario Objeto Usuario con los detalles del usuario.
     * @return El objeto Usuario creado.
     */
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    /**
     * Actualiza los detalles de un usuario existente.
     * @param usuario Objeto Usuario con los nuevos detalles.
     * @return El objeto Usuario actualizado.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) throws RecordNotFoundException {
        Usuario updatedUsuario = usuarioService.updateUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUsuario);
    }

    /**
     * Elimina un usuario específico por su ID.
     * @param id ID del usuario que se desea eliminar.
     * @return Estado HTTP indicando el resultado de la operación.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteUsuario(@PathVariable Long id) throws RecordNotFoundException {
        usuarioService.deleteUsuario(id);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Busca usuarios por su nombre.
     * @param nombre Nombre del usuario que se desea buscar.
     * @return Lista de objetos Usuario que coinciden con el nombre proporcionado.
     */
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Usuario>> findUsuariosByNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.findUsuariosByNombre(nombre);
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Valida las credenciales de un usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return El objeto Usuario si las credenciales son válidas, o estado HTTP 401 si no lo son.
     */
    @PostMapping("/validar")
    public ResponseEntity<Usuario> validateUsuario(@RequestParam String correoElectronico, @RequestParam String contrasena) {
        Optional<Usuario> usuario = usuarioService.findUsuarioByCorreoElectronico(correoElectronico);
        if (usuario.isPresent() && PasswordUtil.checkPassword(contrasena, usuario.get().getContrasena())) {
            return new ResponseEntity<>(usuario.get(), new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Cambia la contraseña de un usuario específico.
     * @param id ID del usuario cuya contraseña se desea cambiar.
     * @param nuevaContrasena Nueva contraseña para el usuario.
     * @return El objeto Usuario actualizado con la nueva contraseña.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @PutMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Usuario> changePassword(@PathVariable Long id, @RequestParam String nuevaContrasena) throws RecordNotFoundException {
        Usuario usuario = usuarioService.changePassword(id, nuevaContrasena);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

}
