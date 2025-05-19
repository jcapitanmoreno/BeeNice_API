package com.github.jcapitanmoreno.beenice_api.services;

import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.ChatGrupal;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import com.github.jcapitanmoreno.beenice_api.repositories.ChatGrupalRepository;
import com.github.jcapitanmoreno.beenice_api.repositories.GrupoRepository;
import com.github.jcapitanmoreno.beenice_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatGrupalService {

    @Autowired
    private ChatGrupalRepository chatGrupalRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ChatGrupal sendMessage(Long grupoId, Long usuarioId, String mensaje) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (grupoOptional.isPresent() && usuarioOptional.isPresent()) {
            ChatGrupal chatGrupal = new ChatGrupal();
            chatGrupal.setIdGrupo(grupoOptional.get());
            chatGrupal.setIdUsuario(usuarioOptional.get());
            chatGrupal.setMensaje(mensaje);
            chatGrupal.setFechaEnvio(java.time.LocalDateTime.now().toString()); // Fecha actual
            return chatGrupalRepository.save(chatGrupal);
        } else {
            throw new RecordNotFoundException("Grupo o usuario no encontrado", grupoId);
        }
    }


    public List<ChatGrupal> getMessagesByGrupoId(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isPresent()) {
            return chatGrupalRepository.findAll().stream()
                    .filter(chat -> chat.getIdGrupo().getId().equals(grupoId))
                    .peek(chat -> {
                        Usuario usuario = usuarioRepository.findById(chat.getIdUsuario().getId()).orElse(null);
                        if (usuario != null) {
                            chat.getIdUsuario().setNombre(usuario.getNombre());
                        }
                    })
                    .toList();
        } else {
            throw new RecordNotFoundException("No se encontró el grupo con el ID proporcionado", grupoId);
        }
    }



    public void deleteMessage(Long mensajeId) throws RecordNotFoundException {
        Optional<ChatGrupal> chatOptional = chatGrupalRepository.findById(mensajeId);
        if (chatOptional.isPresent()) {
            chatGrupalRepository.deleteById(mensajeId);
        } else {
            throw new RecordNotFoundException("No se encontró el mensaje con el ID proporcionado", mensajeId);
        }
    }
    public String getUsuarioNombreById(Long usuarioId) throws RecordNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get().getNombre();
        } else {
            throw new RecordNotFoundException("Usuario no encontrado", usuarioId);
        }
    }
}
