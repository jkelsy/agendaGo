package km.software.agenda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import km.software.agendago.EmailTareaService;
import km.software.agendago.db.Historial;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.HistorialFacade;
import km.software.agendago.db.service.facade.TareaFacade;

@Named("finalizarTareaController")
@ViewScoped
public class FinalizarTareaController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FinalizarTareaController.class.getName()); 
    private Tarea tarea;
    private Long idTarea;
    private List<Usuario> responsables;
    private String observacion;
    
    
    @Inject 
    private TareaFacade tareaService;
    
    @Inject 
    private AgendaFacade agendaService;    
    
    @Inject 
    private HistorialFacade historicoService;
    
    @Inject
    private EmailTareaService emailService;
    
    public void cargar(){       
        this.tarea = tareaService.find(idTarea);
        responsables = agendaService.findUsuariosByTarea(idTarea);
        this.observacion = "";         
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Usuario> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Usuario> responsables) {
        this.responsables = responsables;
    } 
    
    public String finalizar(){
        Historial historico = new Historial();
        historico.setTarea(tarea);
        historico.setUsuario(agendaService.getUsuarioActual());
        historico.setObservacion(observacion);
        historico.setEstado(agendaService.findEstadoTareaByNombre("FINALIZADA"));       
        historico.setFecha(new java.util.Date());
        
        historicoService.create(historico);        
        tarea.setEstado(historico.getEstado());
        tareaService.edit(tarea);
        
        for (Usuario usr : responsables) {
            String texto = "<b>AgendaGo</b>";
                texto += "<br/>Señor: <b>"+usr.getPersona().getNombre() +"</b>";
                texto += "<br/>Le informamos que tiene una terea finalizada";
                texto += "<br/>Descripcion: "+tarea.getDescripcion();
                texto += "<br/>Fecha Inicial: "+ tarea.getFechaInicial();
                texto += "<br/>Fecha Final: "+ tarea.getFechaFinal();                
                texto += "<br/>Creador: "+tarea.getCreador().getLogin();
                
                emailService.sendEmail(usr.getPersona().getEmail(), 
                        "agendagoj2km@gmail.com", 
                        "AgendaGo, tarea finalizada", texto);
        } 
        
        return "/pages/main?faces-redirect=true&amp";
    }
    
    
   
}