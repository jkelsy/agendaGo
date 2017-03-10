/**
 * This file was generated by the JPA Modeler
 */
package km.software.agendago.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * @author jkelsy
 */
@Entity(name = "ArchivoTarea")
@NamedQuery(name = "ArchivoTarea.findAllByTarea", query = "Select e from ArchivoTarea e where e.tarea.id = :tareaID")
public class ArchivoTarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private Date fecha;

    @ManyToOne(targetEntity = Tarea.class)
    @JoinColumn(name = "TAREA_ID")
    private Tarea tarea;

    @ManyToOne(targetEntity = Archivo.class)
    @JoinColumn(name = "ARCHIVO_ID")
    private Archivo archivo;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tarea getTarea() {
        return this.tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Archivo getArchivo() {
        return this.archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
