package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long> {

    /**
     * Encuentra un usuario por su nombre de usuario.
     * @param username el nombre de usuario del usuario.
     * @return el usuario con el nombre de usuario especificado.
     */
    UsuarioGrupo findByUsername(String username);

    /**
     * Encuentra un usuario por su correo electrónico.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el correo electrónico especificado.
     */
    UsuarioGrupo findByEmail(String email);

    /**
     * Encuentra un usuario por su nombre de usuario o correo electrónico.
     * @param username el nombre de usuario del usuario.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el nombre de usuario o correo electrónico especificado.
     */
    UsuarioGrupo findByUsernameOrEmail(String username, String email);

    /**
     * Encuentra un usuario por su nombre de usuario y correo electrónico.
     * @param username el nombre de usuario del usuario.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el nombre de usuario y correo electrónico especificado.
     */
    UsuarioGrupo findByUsernameAndEmail(String username, String email);

    /**
     * Encuentra un usuario por su nombre de usuario y grupo.
     * @param username el nombre de usuario del usuario.
     * @param grupo el grupo del usuario.
     * @return el usuario con el nombre de usuario y grupo especificado.
     */
    UsuarioGrupo findByUsernameAndGrupo(String username, String grupo);

    /**
     * Encuentra un usuario por su correo electrónico y grupo.
     * @param email el correo electrónico del usuario.
     * @param grupo el grupo del usuario.
     * @return el usuario con el correo electrónico y grupo especificado.
     */
    UsuarioGrupo findByEmailAndGrupo(String email, String grupo);

    /**
     * Encuentra un usuario por su nombre de usuario o grupo.
     * @param username el nombre de usuario del usuario.
     * @param grupo el grupo del usuario.
     * @return el usuario con el nombre de usuario o grupo especificado.
     */
    UsuarioGrupo findByUsernameOrGrupo(String username, String grupo);
    /**
     * Encuentra un usuario por su correo electrónico o grupo.
     * @param email el correo electrónico del usuario.
     * @param grupo el grupo del usuario.
     * @return el usuario con el correo electrónico o grupo especificado.
     */
    UsuarioGrupo findByEmailOrGrupo(String email, String grupo);
}
