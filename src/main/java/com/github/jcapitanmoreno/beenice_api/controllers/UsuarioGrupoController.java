package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.services.UsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuario-grupo")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    /**
     * Agrega un usuario a un grupo específico.
     * @param usuarioId ID del usuario que se desea agregar al grupo.
     * @param grupoId ID del grupo al que se desea agregar el usuario.
     * @return El objeto Grupo actualizado con el usuario agregado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    @PostMapping("/{usuarioId}/grupo/{grupoId}")
    public ResponseEntity<Grupo> addUsuarioToGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.addUsuarioToGrupo(usuarioId, grupoId);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Elimina un usuario de un grupo específico.
     * @param usuarioId ID del usuario que se desea eliminar del grupo.
     * @param grupoId ID del grupo del que se desea eliminar el usuario.
     * @return El objeto Grupo actualizado con el usuario eliminado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    @DeleteMapping("/{usuarioId}/grupo/{grupoId}")
    public ResponseEntity<Grupo> removeUsuarioFromGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.removeUsuarioFromGrupo(usuarioId, grupoId);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene todos los grupos a los que pertenece un usuario específico.
     * @param usuarioId ID del usuario cuyos grupos se desean obtener.
     * @return Conjunto de objetos Grupo asociados al usuario.
     * @throws RecordNotFoundException Si el usuario no existe.
     */
    @GetMapping("/usuario/{usuarioId}/grupos")
    public ResponseEntity<Set<Grupo>> getGruposByUsuarioId(@PathVariable Long usuarioId) throws RecordNotFoundException {
        Set<Grupo> grupos = usuarioGrupoService.getGruposByUsuarioId(usuarioId);
        return new ResponseEntity<>(grupos, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene todos los usuarios que pertenecen a un grupo específico.
     * @param grupoId ID del grupo cuyos usuarios se desean obtener.
     * @return Conjunto de objetos Usuario asociados al grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/grupo/{grupoId}/usuarios")
    public ResponseEntity<Set<Usuario>> getUsuariosByGrupoId(@PathVariable Long grupoId) throws RecordNotFoundException {
        Set<Usuario> usuarios = usuarioGrupoService.getUsuariosByGrupoId(grupoId);
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Permite a un usuario unirse a un grupo mediante su código único.
     * @param usuarioId ID del usuario que se desea unir al grupo.
     * @param codigoGrupo Código único del grupo al que se desea unir el usuario.
     * @return El objeto Grupo actualizado con el usuario agregado.
     * @throws RecordNotFoundException Si el usuario o el grupo no existen.
     */
    @PostMapping("/{usuarioId}/join/{codigoGrupo}")
    public ResponseEntity<Grupo> joinGrupoByCodigo(@PathVariable Long usuarioId, @PathVariable String codigoGrupo) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.joinGrupoByCodigo(usuarioId, codigoGrupo);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }
}