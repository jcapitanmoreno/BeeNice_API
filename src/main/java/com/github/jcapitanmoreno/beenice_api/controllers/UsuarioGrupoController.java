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

    @PostMapping("/{usuarioId}/grupo/{grupoId}")
    public ResponseEntity<Grupo> addUsuarioToGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.addUsuarioToGrupo(usuarioId, grupoId);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{usuarioId}/grupo/{grupoId}")
    public ResponseEntity<Grupo> removeUsuarioFromGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.removeUsuarioFromGrupo(usuarioId, grupoId);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}/grupos")
    public ResponseEntity<Set<Grupo>> getGruposByUsuarioId(@PathVariable Long usuarioId) throws RecordNotFoundException {
        Set<Grupo> grupos = usuarioGrupoService.getGruposByUsuarioId(usuarioId);
        return new ResponseEntity<>(grupos, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/grupo/{grupoId}/usuarios")
    public ResponseEntity<Set<Usuario>> getUsuariosByGrupoId(@PathVariable Long grupoId) throws RecordNotFoundException {
        Set<Usuario> usuarios = usuarioGrupoService.getUsuariosByGrupoId(grupoId);
        return new ResponseEntity<>(usuarios, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{usuarioId}/join")
    public ResponseEntity<Grupo> joinGrupoByCodigo(@PathVariable Long usuarioId, @RequestParam String codigoGrupo) throws RecordNotFoundException {
        Grupo grupo = usuarioGrupoService.joinGrupoByCodigo(usuarioId, codigoGrupo);
        return new ResponseEntity<>(grupo, new HttpHeaders(), HttpStatus.OK);
    }
}