package km.software.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import km.software.agendago.db.Persona;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.EstadoTareaFacade;
import km.software.agendago.db.service.facade.PermisoFacade;
import km.software.agendago.db.service.facade.PersonaFacade;
import km.software.agendago.db.service.facade.ResponsableFacade;
import km.software.agendago.db.service.facade.RolFacade;
import km.software.agendago.db.service.facade.TareaFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;

@Named("agendaController")
@ViewScoped
public class AgendaController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AgendaController.class.getName()); 
    private Usuario usuario;
    private Persona persona;
    private Tarea tarea;
    private java.util.Date hoy;
    private List<Usuario> responsables;
    
    @Inject
    private PersonaFacade personaService;
    
    @Inject
    private UsuarioFacade usuarioService;
    
    @Inject
    private PermisoFacade permisoService;
    
    @Inject
    private RolFacade rolService;
    
    @Inject 
    private TareaFacade tareaService;
    
    @Inject 
    private ResponsableFacade responsableService;
    
    @Inject EstadoTareaFacade estadoService;
    
    @Inject 
    private AgendaFacade agendaService;    
    
    @PostConstruct
    public void cargar(){
        usuario = new Usuario();
        persona = new Persona();
        tarea = new Tarea();
        if(responsables != null && !responsables.isEmpty()){
            responsables.clear();
        }
    }

    public Date getHoy() {
        if(hoy == null){
            hoy = new java.util.Date();
        }
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }    

    public Usuario getUsuario() {        
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }    

    public Persona getPersona() {        
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }    

    public List<Usuario> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Usuario> responsables) {
        this.responsables = responsables;
    }   
    
    public String seleccionarTarea(Tarea tarea){
        return "/pages/editarTareaForm?faces-redirect=true&amp&id="+tarea.getId();        
    }
    
    public String observacionTarea(Tarea tarea){
        return "/pages/observacionTareaForm?faces-redirect=true&amp&id="+tarea.getId();        
    }
    
    public String verTarea(Tarea tarea){
        return "/pages/verTarea?faces-redirect=true&amp&id="+tarea.getId();        
    }
    
    public String archivosTarea(Tarea tarea){
        return "/pages/archivosTareaForm?faces-redirect=true&amp&id="+tarea.getId();        
    }
    
    public String finalizarTarea(Tarea tarea){        
        return "/pages/tareaFinalizar?faces-redirect=true&amp&id="+tarea.getId();
    }
    
    public List<Usuario> completeUsuario(String query) {
        List<Usuario> allUsuarios = usuarioService.findAll();
        List<Usuario> filteredUsuarios = new ArrayList<>();
         
        for (int i = 0; i < allUsuarios.size(); i++) {
            Usuario usr = allUsuarios.get(i);
            if(usr.getLogin().toLowerCase().startsWith(query)) {
                filteredUsuarios.add(usr);
            }
        }         
        return filteredUsuarios;
    }
    
}