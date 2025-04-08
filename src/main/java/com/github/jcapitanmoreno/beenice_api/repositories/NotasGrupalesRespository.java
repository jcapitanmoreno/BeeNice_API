package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.NotaGrupal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotasGrupalesRespository extends JpaRepository<NotaGrupal, Integer> {
    /**
     * Encuentra una nota grupal por su ID.
     * @param id el ID de la nota grupal.
     * @return la nota grupal con el ID especificado.
     */
    NotaGrupal findById(int id);

    /**
     * Encuentra una nota grupal por su contenido.
     * @param contenido el contenido de la nota grupal.
     * @return la nota grupal con el contenido especificado.
     */
    NotaGrupal findByContenido(String contenido);

}
