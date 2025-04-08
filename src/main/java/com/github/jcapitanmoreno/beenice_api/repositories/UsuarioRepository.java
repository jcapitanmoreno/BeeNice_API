package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra un usuario por su nombre de usuario.
     * @param username el nombre de usuario del usuario.
     * @return el usuario con el nombre de usuario especificado.
     */
    Usuario findByUsername(String username);

    /**
     * Encuentra un usuario por su correo electrónico.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el correo electrónico especificado.
     */
    Usuario findByEmail(String email);

    /**
     * Encuentra un usuario por su nombre de usuario o correo electrónico.
     * @param username el nombre de usuario del usuario.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el nombre de usuario o correo electrónico especificado.
     */
    Usuario findByUsernameOrEmail(String username, String email);

    /**
     * Encuentra un usuario por su nombre de usuario y correo electrónico.
     * @param username el nombre de usuario del usuario.
     * @param email el correo electrónico del usuario.
     * @return el usuario con el nombre de usuario y correo electrónico especificado.
     */
    Usuario findByUsernameAndEmail(String username, String email);

}
