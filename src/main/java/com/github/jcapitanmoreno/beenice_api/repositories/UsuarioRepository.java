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

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

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
