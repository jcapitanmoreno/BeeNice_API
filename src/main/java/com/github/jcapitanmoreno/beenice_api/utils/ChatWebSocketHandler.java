package com.github.jcapitanmoreno.beenice_api.utils;


import com.github.jcapitanmoreno.beenice_api.Services.ChatGrupalService;
import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private ChatGrupalService chatGrupalService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // Agregar la sesión a la lista
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(";", 3); // Ejemplo: "grupoId;usuarioId;mensaje"
        if (parts.length == 3) {
            Long grupoId = Long.parseLong(parts[0]);
            Long usuarioId = Long.parseLong(parts[1]);
            String mensaje = parts[2];

            try {
                // Guardar el mensaje en la base de datos
                chatGrupalService.sendMessage(grupoId, usuarioId, mensaje);

                // Obtener el nombre del usuario
                String nombreUsuario = chatGrupalService.getUsuarioNombreById(usuarioId);

                // Crear un objeto JSON con el mensaje y el nombre del usuario
                String jsonResponse = String.format(
                        "{\"usuarioId\": %d, \"nombreUsuario\": \"%s\", \"mensaje\": \"%s\"}",
                        usuarioId, nombreUsuario, mensaje
                );

                // Difundir el mensaje a todas las sesiones conectadas
                for (WebSocketSession webSocketSession : sessions) {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(jsonResponse));
                    }
                }
            } catch (RecordNotFoundException e) {
                session.sendMessage(new TextMessage("Error: " + e.getMessage()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        // Eliminar la sesión de la lista
        sessions.remove(session);

        // Notificar a los demás usuarios sobre la desconexión
        String disconnectMessage = "{\"type\": \"disconnect\", \"message\": \"Un usuario se ha desconectado\"}";
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(disconnectMessage));
            }
        }
    }
}