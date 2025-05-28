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

    /**
     * Envía un mensaje en un chat grupal.
     * @param grupoId ID del grupo al que pertenece el mensaje.
     * @param usuarioId ID del usuario que envía el mensaje.
     * @param mensaje Contenido del mensaje.
     * @return El objeto ChatGrupal creado y guardado.
     * @throws RecordNotFoundException Si el grupo o el usuario no existen.
     */
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

    /**
     * Obtiene todos los mensajes de un grupo específico.
     * @param grupoId ID del grupo del cual se quieren obtener los mensajes.
     * @return Lista de objetos ChatGrupal correspondientes al grupo.
     * @throws RecordNotFoundException Si el grupo no existe.
     */
    @GetMapping("/messages/{grupoId}")
    public ResponseEntity<List<ChatGrupal>> getMessagesByGrupoId(@PathVariable Long grupoId) {
        try {
            List<ChatGrupal> messages = chatGrupalService.getMessagesByGrupoId(grupoId);
            return ResponseEntity.ok(messages);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Elimina un mensaje específico por su ID.
     * @param mensajeId ID del mensaje que se desea eliminar.
     * @return Respuesta sin contenido si la eliminación es exitosa.
     * @throws RecordNotFoundException Si el mensaje no existe.
     */
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