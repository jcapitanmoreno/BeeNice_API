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

    @GetMapping
    public ResponseEntity<List<Grupo>> getAllGrupos() {
        List<Grupo> grupos = grupoService.getAllGrupos();
        return new ResponseEntity<>(grupos, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) throws RecordNotFoundException {
        Grupo grupo = grupoService.getGrupoById(id);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Grupo> getGrupoByCodigo(@PathVariable String codigo) throws RecordNotFoundException {
        Grupo grupo = grupoService.getGrupoByCodigo(codigo);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody Grupo grupo) {
        Grupo createdGrupo = grupoService.createGrupo(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrupo);
    }

    @PutMapping
    public ResponseEntity<Grupo> updateGrupo(@RequestBody Grupo grupo) throws RecordNotFoundException {
        Grupo updatedGrupo = grupoService.updateGrupo(grupo);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGrupo);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteGrupo(@PathVariable Long id) throws RecordNotFoundException {
        grupoService.deleteGrupo(id);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/{grupoId}/creador/{creadorId}")
    public ResponseEntity<Grupo> assignarCreadorToGrupo(@PathVariable Long grupoId, @PathVariable Long creadorId) throws RecordNotFoundException {
        Usuario creador = usuarioService.getUsuarioById(creadorId);
        Grupo grupo = grupoService.assignarCreadorToGrupo(grupoId, creador);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}/codigo")
    public ResponseEntity<String> getCodigoGrupoById(@PathVariable Long id) throws RecordNotFoundException {
        String codigo = grupoService.getCodigoGrupoById(id);
        return new ResponseEntity<>(codigo, new HttpHeaders(), HttpStatus.OK);
    }
}