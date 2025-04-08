package com.github.jcapitanmoreno.beenice_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "grupo")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "codigo_grupo", length = 50)
    private String codigoGrupo;

    @Size(max = 255)
    @Column(name = "imagen")
    private String imagen;

    @Size(max = 50)
    @Column(name = "fecha", length = 50)
    private String fecha;

    @Lob
    @Column(name = "descripcion_general")
    private String descripcionGeneral;

    @OneToMany(mappedBy = "idGrupo")
    private Set<ChatGrupal> chatGrupals = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idGrupo")
    private Set<Gasto> gastos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idGrupo")
    private Set<NotaGrupal> notaGrupals = new LinkedHashSet<>();

    @ManyToMany
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    public Set<ChatGrupal> getChatGrupals() {
        return chatGrupals;
    }

    public void setChatGrupals(Set<ChatGrupal> chatGrupals) {
        this.chatGrupals = chatGrupals;
    }

    public Set<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(Set<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Set<NotaGrupal> getNotasGrupales() {
        return notaGrupals;
    }

    public void setNotasGrupales(Set<NotaGrupal> notaGrupals) {
        this.notaGrupals = notaGrupals;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}