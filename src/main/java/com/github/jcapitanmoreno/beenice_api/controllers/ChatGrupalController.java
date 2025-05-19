package com.github.jcapitanmoreno.beenice_api.controllers;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.ChatGrupal;
import com.github.jcapitanmoreno.beenice_api.services.ChatGrupalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-grupal")
public class ChatGrupalController {

    @Autowired
    private ChatGrupalService chatGrupalService;

    @PostMapping("/send")
    public ResponseEntity<ChatGrupal> sendMessage(
            @RequestParam Long grupoId,
            @RequestParam Long usuarioId,
            @RequestParam String mensaje) {
        try {
            ChatGrupal chatGrupal = chatGrupalService.sendMessage(grupoId, usuarioId, mensaje);
            return ResponseEntity.ok(chatGrupal);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/messages/{grupoId}")
    public ResponseEntity<List<ChatGrupal>> getMessagesByGrupoId(@PathVariable Long grupoId) {
        try {
            List<ChatGrupal> messages = chatGrupalService.getMessagesByGrupoId(grupoId);
            return ResponseEntity.ok(messages);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{mensajeId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long mensajeId) {
        try {
            chatGrupalService.deleteMessage(mensajeId);
            return ResponseEntity.noContent().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
}