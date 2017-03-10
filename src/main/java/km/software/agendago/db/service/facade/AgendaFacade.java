package km.software.agendago.db.service.facade;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import km.software.agendago.db.ArchivoTarea;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.Historial;
import km.software.agendago.db.Observacion;
import km.software.agendago.db.Responsable;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.Usuario;


/**
 *
 * @author jkelsy
 */
@Stateless
@Named("agendaFacade")
public class AgendaFacade implements Serializable {
    
    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;
    
    @Inject
    private ArchivoTareaFacade archivoTareaService;
    
    @Inject
    private HttpServletRequest request;    
    
    public List<Responsable> findResponsablesByTarea(Long tareaID){
        return em.createNamedQuery("Responsable.findByTarea", Responsable.class).
                setParameter("tareaID", tareaID).
                getResultList();
    }
    
    public String findStringResponsablesByTarea(Long tareaID){
        String result = "";
        List<Responsable> resposables = em.createNamedQuery("Responsable.findByTarea", Responsable.class).
                setParameter("tareaID", tareaID).
                getResultList();
        
        
        for (Responsable r : resposables) {
            result += r.getUsuario().getLogin()+", ";
        }
        
        return result;
    }
    
    public List<Historial> findHistorialByTarea(Long tareaID){
        return em.createNamedQuery("Historial.findByTarea", Historial.class).
                setParameter("tareaID", tareaID).
                getResultList();
    }
    
    public List<Observacion> findObservacionByTarea(Long tareaID){
        return em.createNamedQuery("Observacion.findByTarea", Observacion.class).
                setParameter("tareaID", tareaID).
                getResultList();
    }
    
    public List<Usuario> findUsuariosByTarea(Long tareaID){
        return em.createNamedQuery("Responsable.findUsuariosByTarea", Usuario.class).
                setParameter("tareaID", tareaID).
                getResultList();
    }
    
    public Usuario getUsuarioActual(){
        Usuario temp;        
        try{            
            temp = em.createNamedQuery("Usuario.findByLogin", Usuario.class).
                setParameter("login",FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).
                getSingleResult();
        }catch(Exception e){
            temp = null;
            System.out.println("Mierda,Mierda"+e.getMessage());
        }        
        return temp;
    }
    
    public EstadoTarea findEstadoTareaByNombre(String nombre){
        EstadoTarea temp;
        try{
            temp = em.createNamedQuery("EstadoTarea.findByNombre", EstadoTarea.class).
                setParameter("nombre", nombre).
                getSingleResult();
        }catch(Exception e){
            temp = null;
        }        
        return temp;
    }
    
    public List<Tarea> findTareasByEstadoID(Long estadoID){        
        return em.createNamedQuery("Tarea.findByEstadoID", Tarea.class).
                setParameter("estadoID", estadoID).
                getResultList();        
    }
    
    public List<Tarea> findTareasSinFinalizarByUsuarioActual(){        
        return em.createNamedQuery("Tarea.findSinFinalizarByUsuario", Tarea.class).
                setParameter("login", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).
                getResultList();        
    }
    
    public List<ArchivoTarea> findArchivosByTarea(Long tareaID){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tareaID", tareaID);
        return archivoTareaService.findByNamedQuery("ArchivoTarea.findAllByTarea", parameters);
    }
    
    
    
}
