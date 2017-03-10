/**
 * This file was generated by the JPA Modeler
 */
package km.software.agendago.db;

import java.io.Serializable;
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
@Entity(name = "Permiso")
@Table(name = "sec_permiso")
@NamedQueries({
    @NamedQuery(name = "Permiso.findByUsuarioAndRol", query = "Select e from Permiso e where e.usuario.id = :usuarioID and e.rol.id = :rolID ")
    ,@NamedQuery(name = "Permiso.findAllByUsuario", query = "Select e from Permiso e where e.usuario.id = :usuarioID")
    ,@NamedQuery(name = "Permiso.findAllRolesByUsuario", query = "Select e.rol from Permiso e where e.usuario.id = :usuarioID")})
public class Permiso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "USUARIO_USR_ID")
    private Usuario usuario;

    @ManyToOne(targetEntity = Rol.class)
    @JoinColumn(name = "ROL_ROL_ID")
    private Rol rol;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}