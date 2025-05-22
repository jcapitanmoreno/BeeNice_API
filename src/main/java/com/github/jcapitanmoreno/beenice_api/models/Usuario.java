package com.github.jcapitanmoreno.beenice_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonIgnoreProperties({"grupos", "chatGrupals", "pagos"})
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Size(max = 100)
    @Column(name = "correo_electronico", length = 100, unique = true)
    private String correoElectronico;

    @Size(max = 100)
    @Column(name = "contrasena", length = 100)
    private String contrasena;


    @Column(name = "imagen", columnDefinition = "TEXT")
    private String imagen;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Pago> pagos = new LinkedHashSet<>();


    @OneToMany(mappedBy = "idUsuario")
    private Set<ChatGrupal> chatGrupals = new LinkedHashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "usuarios")
    private Set<Grupo> grupos = new LinkedHashSet<>();



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Set<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        this.pagos = pagos;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Set<ChatGrupal> getChatGrupals() {
        return chatGrupals;
    }

    public void setChatGrupals(Set<ChatGrupal> chatGrupals) {
        this.chatGrupals = chatGrupals;
    }
}