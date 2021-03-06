/**
 * This file was generated by the JPA Modeler
 */
package km.software.agendago.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author jkelsy
 */
@Entity(name = "Responsable")
@Table(name = "responsable")
@NamedQueries({
    @NamedQuery(name = "Responsable.findByTarea", query = "Select e from Responsable e where e.tarea.id = :tareaID")
    ,@NamedQuery(name = "Responsable.findUsuariosByTarea", query = "Select e.usuario from Responsable e where e.tarea.id = :tareaID")})
public class Responsable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Tarea.class)
    @JoinColumn(name = "TAREA_ID")
    private Tarea tarea;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tarea getTarea() {
        return this.tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
