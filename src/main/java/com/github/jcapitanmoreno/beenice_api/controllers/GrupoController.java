package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.services.GrupoService;
import com.github.jcapitanmoreno.beenice_api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todos los grupos registrados en el sistema.
     * @return Lista de objetos Grupo.
     */
    @GetMapping
    public ResponseEntity<List<Grupo>> getAllGrupos() {
        List<Grupo> grupos = grupoService.getAllGrupos();
        return new ResponseEntity<>(grupos, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene un grupo específico por su ID.
     * @param id ID del grupo que se desea obtener.
     * @return El objeto Grupo correspondiente al ID proporcionado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) throws RecordNotFoundException {
        Grupo grupo = grupoService.getGrupoById(id);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene un grupo específico por su código único.
     * @param codigo Código único del grupo que se desea obtener.
     * @return El objeto Grupo correspondiente al código proporcionado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Grupo> getGrupoByCodigo(@PathVariable String codigo) throws RecordNotFoundException {
        Grupo grupo = grupoService.getGrupoByCodigo(codigo);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Crea un nuevo grupo en el sistema.
     * @param grupo Objeto Grupo con los detalles del grupo.
     * @return El objeto Grupo creado.
     */
    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody Grupo grupo) {
        Grupo createdGrupo = grupoService.createGrupo(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrupo);
    }

    /**
     * Actualiza los detalles de un grupo existente.
     * @param grupo Objeto Grupo con los nuevos detalles.
     * @return El objeto Grupo actualizado.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @PutMapping
    public ResponseEntity<Grupo> updateGrupo(@RequestBody Grupo grupo) throws RecordNotFoundException {
        Grupo updatedGrupo = grupoService.updateGrupo(grupo);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGrupo);
    }

    /**
     * Elimina un grupo específico por su ID.
     * @param id ID del grupo que se desea eliminar.
     * @return Estado HTTP indicando el resultado de la operación.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteGrupo(@PathVariable Long id) throws RecordNotFoundException {
        grupoService.deleteGrupo(id);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Asigna un creador a un grupo específico.
     * @param grupoId ID del grupo al que se asignará el creador.
     * @param creadorId ID del usuario que será asignado como creador.
     * @return El objeto Grupo actualizado con el creador asignado.
     * @throws RecordNotFoundException Si el grupo o el usuario no existen.
     */
    @PostMapping("/{grupoId}/creador/{creadorId}")
    public ResponseEntity<Grupo> assignarCreadorToGrupo(@PathVariable Long grupoId, @PathVariable Long creadorId) throws RecordNotFoundException {
        Usuario creador = usuarioService.getUsuarioById(creadorId);
        Grupo grupo = grupoService.assignarCreadorToGrupo(grupoId, creador);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Obtiene el código único de un grupo específico por su ID.
     * @param id ID del grupo del cual se desea obtener el código.
     * @return El código único del grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/{id}/codigo")
    public ResponseEntity<String> getCodigoGrupoById(@PathVariable Long id) throws RecordNotFoundException {
        String codigo = grupoService.getCodigoGrupoById(id);
        return new ResponseEntity<>(codigo, new HttpHeaders(), HttpStatus.OK);
    }
}