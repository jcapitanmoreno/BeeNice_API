package com.github.jcapitanmoreno.beenice_api.Services;


import com.github.jcapitanmoreno.beenice_api.exceptions.RecordNotFoundException;
import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import com.github.jcapitanmoreno.beenice_api.models.NotaGrupal;
import com.github.jcapitanmoreno.beenice_api.repositories.GrupoRepository;
import com.github.jcapitanmoreno.beenice_api.repositories.NotasGrupalesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class NotasGrupalesService {

    @Autowired
    private NotasGrupalesRespository notasGrupalesRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    // Crear o recuperar la nota grupal de un grupo
    public NotaGrupal createOrGetNotaGrupal(Long grupoId) throws RecordNotFoundException {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        if (grupoOptional.isEmpty()) {
            throw new RecordNotFoundException("No se encontró el grupo con el ID proporcionado", grupoId);
        }

        Grupo grupo = grupoOptional.get();
        return notasGrupalesRepository.findAll().stream()
                .filter(nota -> nota.getIdGrupo().getId().equals(grupoId))
                .findFirst()
                .orElseGet(() -> {
                    NotaGrupal nuevaNota = new NotaGrupal();
                    nuevaNota.setIdGrupo(grupo);
                    nuevaNota.setTexto(""); // Texto inicial vacío
                    return notasGrupalesRepository.save(nuevaNota);
                });
    }

    // Obtener la nota grupal de un grupo
    public NotaGrupal getNotaGrupalByGrupoId(Long grupoId) throws RecordNotFoundException {
        return notasGrupalesRepository.findAll().stream()
                .filter(nota -> nota.getIdGrupo().getId().equals(grupoId))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No se encontró una nota grupal para el grupo con ID proporcionado", grupoId));
    }

    // Actualizar la nota grupal de un grupo
    public NotaGrupal updateNotaGrupal(Long grupoId, String nuevoTexto) throws RecordNotFoundException {
        NotaGrupal notaGrupal = getNotaGrupalByGrupoId(grupoId);
        notaGrupal.setTexto(nuevoTexto);
        return notasGrupalesRepository.save(notaGrupal);
    }

    // Eliminar la nota grupal de un grupo
    public void deleteNotaGrupal(Long grupoId) throws RecordNotFoundException {
        NotaGrupal notaGrupal = getNotaGrupalByGrupoId(grupoId);
        notasGrupalesRepository.delete(notaGrupal);
    }
}
