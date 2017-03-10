package km.software.agenda.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.EstadoTareaFacade;
import km.software.agendago.db.service.facade.TareaFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;

@Named("reporteController")
@ViewScoped
public class ReporteController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ReporteController.class.getName()); 
    
    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;
    
    @Inject
    private TareaFacade tareaService;    
    @Inject 
    private EstadoTareaFacade estadoService;    
    @Inject 
    private UsuarioFacade usuarioService;
    
    
    private Date fechaInicial;
    private Date fechaFinal;
    private EstadoTarea estadoSeleccionado;
    private Usuario usuarioSeleccionado;
    
    private List<Tarea> tareas;
    private List<EstadoTarea> estados;
    private List<Usuario> usuarios;

    @PostConstruct
    public void cargar(){        
        fechaFinal = null;
        fechaInicial = null;
        usuarioSeleccionado = null;
        estadoSeleccionado = null;
        estados = estadoService.findAll();
        usuarios = usuarioService.findAll();
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<EstadoTarea> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoTarea> estados) {
        this.estados = estados;
    }

    public EstadoTarea getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(EstadoTarea estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionando) {
        this.usuarioSeleccionado = usuarioSeleccionando;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    
    public void buscar(){
        String consulta = "SELECT distinct(r.tarea) from Responsable r where 1=1 ";
        
        if(usuarioSeleccionado != null){
            consulta +=" and r.usuario.id = "+usuarioSeleccionado.getId();
        }
        
        if(estadoSeleccionado != null){
            consulta +=" and r.tarea.estado.id = "+estadoSeleccionado.getId();
        }
        tareas = em.createQuery(consulta, Tarea.class).getResultList();
        /*if (usuarioSeleccionado != null){
            System.out.println(usuarioSeleccionado.getLogin());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("usuarioId", usuarioSeleccionado.getId());
            tareas = tareaService.findByNamedQuery("Tarea.findAllByResponsable", parameters);
        }else{
            tareas = tareaService.findAll();
        }*/
        
    }
    
    
}