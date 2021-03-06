/**
 * This file was generated by the JPA Modeler
 */
package km.software.agendago.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jkelsy
 */
@Entity(name = "Tarea")
@Table(name = "tarea")
@NamedQueries({
    @NamedQuery(name = "Tarea.findByEstadoID", query = "Select e from Tarea e where e.estado.id = :estadoID")
    ,@NamedQuery(name = "Tarea.findSinFinalizarByUsuario", query = "Select e.tarea from Responsable e where e.tarea.estado.nombre != 'FINALIZADA' and e.usuario.login = :login")
    ,@NamedQuery(name = "Tarea.findAllByResponsable", query = "Select e.tarea from Responsable e where e.usuario.id = :usuarioId")})
@XmlRootElement
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tar_fecha_inicial")
    @Basic
    @NotNull
    private Date fechaInicial;

    @Lob
    @Column(name = "tar_descripcion", length = Integer.MAX_VALUE)
    @Basic
    @NotNull    
    private String descripcion;

    @Column(name = "tar_fecha_final")
    @Basic
    private Date fechaFinal;

    @Column(name = "tar_fecha_creacion")
    @Basic
    private Date fechaCreacion;

    @Column(name = "tar_fecha_aviso")
    @Basic
    private Date fechaAviso;

    @Column(name = "tar_nombre")
    @Basic
    private String nombre;

    @ManyToOne(targetEntity = Proyecto.class)
    @JoinColumn(name = "PROYECTO_ID")
    private Proyecto proyecto;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "CREADOR_ID")
    private Usuario creador;

    @ManyToOne(targetEntity = EstadoTarea.class)
    @JoinColumn(name = "ESTADO_ID")
    private EstadoTarea estado;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicial() {
        return this.fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaFinal() {
        return this.fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAviso() {
        return this.fechaAviso;
    }

    public void setFechaAviso(Date fechaAviso) {
        this.fechaAviso = fechaAviso;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proyecto getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getCreador() {
        return this.creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public EstadoTarea getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

}
