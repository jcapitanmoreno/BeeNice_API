package com.github.jcapitanmoreno.beenice_api.controllers;

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

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) throws RecordNotFoundException {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return new ResponseEntity<>(usuario, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) throws RecordNotFoundException {
        Usuario updatedUsuario = usuarioService.updateUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUsuario);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUsuario(@PathVariable Long id) throws RecordNotFoundException {
        usuarioService.deleteUsuario(id);
        return HttpStatus.ACCEPTED;
    }
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Usuario>> findUsuariosByNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.findUsuariosByNombre(nombre);
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/validar")
    public ResponseEntity<Usuario> validateUsuario(@RequestParam String correoElectronico, @RequestParam String contrasena) {
        Optional<Usuario> usuario = usuarioService.findUsuarioByCorreoElectronico(correoElectronico);
        if (usuario.isPresent() && usuario.get().getContrasena().equals(contrasena)) {
            return new ResponseEntity<>(usuario.get(), new HttpHeaders(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Usuario> changePassword(@PathVariable Long id, @RequestParam String nuevaContrasena) throws RecordNotFoundException {
        Usuario usuario = usuarioService.changePassword(id, nuevaContrasena);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

}
