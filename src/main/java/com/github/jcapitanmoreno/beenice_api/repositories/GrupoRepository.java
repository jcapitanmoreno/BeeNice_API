package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    /**
     * Encuentra un grupo por su nombre.
     * @param nombre el nombre del grupo.
     * @return el grupo con el nombre especificado.
     */
    Grupo findByNombre(String nombre);

    /**
     * Encuentra un grupo por su descripción.
     * @param descripcion la descripción del grupo.
     * @return el grupo con la descripción especificada.
     */
    Grupo findByDescripcion(String descripcion);

    /**
     * Encuentra un grupo por su nombre o descripción.
     * @param nombre el nombre del grupo.
     * @param descripcion la descripción del grupo.
     * @return el grupo con el nombre o descripción especificada.
     */
    Grupo findByNombreOrDescripcion(String nombre, String descripcion);
}
