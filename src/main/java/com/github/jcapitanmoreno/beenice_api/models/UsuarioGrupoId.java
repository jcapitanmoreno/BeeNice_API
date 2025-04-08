package com.github.jcapitanmoreno.beenice_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class UsuarioGrupoId implements java.io.Serializable {
    private static final long serialVersionUID = -6029963588743448995L;
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull
    @Column(name = "id_grupo", nullable = false)
    private Integer idGrupo;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuarioGrupoId entity = (UsuarioGrupoId) o;
        return Objects.equals(this.idUsuario, entity.idUsuario) &&
                Objects.equals(this.idGrupo, entity.idGrupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idGrupo);
    }

}