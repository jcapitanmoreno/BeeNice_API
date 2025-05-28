package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuarios cuyo nombre contenga una cadena específica, ignorando mayúsculas y minúsculas.
     * @param nombre Cadena que se desea buscar en los nombres de los usuarios.
     * @return Lista de objetos Usuario que coinciden con el criterio de búsqueda.
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca un usuario por su correo electrónico.
     * @param correoElectronico Correo electrónico del usuario que se desea buscar.
     * @return Un objeto Optional que contiene el Usuario si se encuentra.
     */
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    /**
     * Obtiene datos detallados de un usuario, incluyendo información de sus grupos, gastos y pagos,
     * para generar un archivo PDF.
     * @param usuarioId ID del usuario del cual se quieren obtener los datos.
     * @return Lista de arreglos de objetos que contienen los datos del usuario y sus asociaciones.
     */
    @Query("SELECT u.nombre AS usuarioNombre, u.correoElectronico AS usuarioCorreo, " +
            "g.codigoGrupo AS grupoCodigo, g.descripcionGeneral AS grupoDescripcion, g.nota AS grupoNota, " +
            "ga.descripcion AS gastoDescripcion, ga.total AS gastoTotal, ga.pagado AS gastoPagado, ga.pendiente AS gastoPendiente, " +
            "p.descripcionPago AS pagoDescripcion, p.totalAPagar AS pagoTotal, p.pagadoHastaAhora AS pagoPagado " +
            "FROM Usuario u " +
            "JOIN u.grupos g " +
            "LEFT JOIN g.gastos ga " +
            "LEFT JOIN ga.pagos p " +
            "WHERE u.id = :usuarioId")
    List<Object[]> getUsuarioDataForPdf(@Param("usuarioId") Long usuarioId);

}
