package com.github.jcapitanmoreno.beenice_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_gasto")
    private Gasto idGasto;

    @Size(max = 100)
    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(name = "total_a_pagar", precision = 10, scale = 2)
    private BigDecimal totalAPagar;

    @Column(name = "pagado_hasta_ahora", precision = 10, scale = 2)
    private BigDecimal pagadoHastaAhora;

    @Lob
    @Column(name = "descripcion_pago")
    private String descripcionPago;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Gasto getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Gasto idGasto) {
        this.idGasto = idGasto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public BigDecimal getPagadoHastaAhora() {
        return pagadoHastaAhora;
    }

    public void setPagadoHastaAhora(BigDecimal pagadoHastaAhora) {
        this.pagadoHastaAhora = pagadoHastaAhora;
    }

    public String getDescripcionPago() {
        return descripcionPago;
    }

    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }

}