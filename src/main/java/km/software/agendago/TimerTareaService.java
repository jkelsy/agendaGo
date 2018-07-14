package km.software.agendago;

import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.Responsable;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.TareaFacade;

/**
 *
 * @author jkelsy
 */
@Singleton
public class TimerTareaService {
    
    @Inject 
    private AgendaFacade agendaService; 
    
    @Inject
    private TareaFacade tareaService;
    
    @Inject
    private EmailTareaService emailService;
    
    @Schedule(second="0", minute="0", hour="8,14", persistent = true)
    //@Schedule(second="0", minute="*/2", hour="*", persistent = true)
    public void revisar(){
        java.util.Date hoy = new java.util.Date();        
        EstadoTarea estadoActiva = agendaService.findEstadoTareaByNombre("ACTIVA");
        EstadoTarea estadoAlerta = agendaService.findEstadoTareaByNombre("ALERTA");
        EstadoTarea estadoVencida = agendaService.findEstadoTareaByNombre("VENCIDA");  
                
        List<Tarea> tareasActivas = agendaService.findTareasByEstadoID(estadoActiva.getId());
        
        for(Tarea _tarea: tareasActivas){
            if(hoy.after(_tarea.getFechaFinal())){
                _tarea.setEstado(estadoVencida);
                tareaService.edit(_tarea);
            }else if(hoy.after(_tarea.getFechaAviso())){
                _tarea.setEstado(estadoAlerta);
                tareaService.edit(_tarea);
            }
        }
        
        List<Tarea> tareasAlertas = agendaService.findTareasByEstadoID(estadoAlerta.getId());
        
        for(Tarea _tarea: tareasAlertas){
            if(hoy.after(_tarea.getFechaFinal())){
                _tarea.setEstado(estadoVencida);
                tareaService.edit(_tarea);
            }
        }
    }
    
    //Datos para enviar correo
    //Correo: agendagoj2km@gmail.com
    //Contraseña: 4g3nd4g0j2km
    //Contraseña: Granalianza*.#
    @Schedule(second="0", minute="30", hour="8,14", persistent = true)
    //@Schedule(second="0", minute="*/3", hour="*", persistent = true)
    public void enviarEmails(){
        java.util.Date hoy = new java.util.Date();       
        EstadoTarea estadoAlerta = agendaService.findEstadoTareaByNombre("ALERTA");
        EstadoTarea estadoVencida = agendaService.findEstadoTareaByNombre("VENCIDA");       
        
        List<Tarea> tareasAlertas = agendaService.findTareasByEstadoID(estadoAlerta.getId());        
        
        for(Tarea _tarea: tareasAlertas){            
            
            List<Responsable> responsables = agendaService.findResponsablesByTarea(_tarea.getId());
            
            String txtResponsables = "";
            for(Responsable _res: responsables){
                txtResponsables += _res.getUsuario().getPersona().getNombre()+ " "
                +_res.getUsuario().getPersona().getApellidos()+", ";
            }
            
            for(Responsable _res: responsables){
                String texto = "<b>AgendaGo</b>";
                texto += "<br/>Señor: <b>"+_res.getUsuario().getPersona().getNombre() +"</b>";
                texto += "<br/>Le informamos que tiene una tarea por vencerse en la fecha: <b>"+_tarea.getFechaFinal()+"</b>";
                texto += "<br/>Descripción: "+_tarea.getDescripcion();
                texto += "<br/>Responsables: "+ txtResponsables;
                texto += "<br/>Creador: "+_tarea.getCreador().getLogin();
                //_res.getUsuario().getPersona().getEmail()
                emailService.sendEmail("jkelsy@gmail.com", 
                        "agendagoj2km@gmail.com", 
                        "AgendaGo, tarea por vencerse", texto);
            }            
        }
        
        List<Tarea> tareasVencidas = agendaService.findTareasByEstadoID(estadoVencida.getId());        
        
        for(Tarea _tarea: tareasVencidas){            
           
            List<Responsable> responsables = agendaService.findResponsablesByTarea(_tarea.getId());
            
            String txtResponsables = "";
            for(Responsable _res: responsables){
                txtResponsables += _res.getUsuario().getPersona().getNombre()+ " "
                +_res.getUsuario().getPersona().getApellidos()+", ";
            }
            
            for(Responsable _res: responsables){
                String texto = "<b>AgendaGo</b>";
                texto += "<br/>Señor: <b>"+_res.getUsuario().getPersona().getNombre() +"</b>";
                texto += "<br/>Le informamos que tiene una tarea vencida en la fecha: <b>"+_tarea.getFechaFinal()+"</b>";
                texto += "<br/>Descripción: "+_tarea.getDescripcion();
                texto += "<br/>Responsables: "+ txtResponsables;
                texto += "<br/>Creador: "+_tarea.getCreador().getLogin();
                
                //_res.getUsuario().getPersona().getEmail()
                emailService.sendEmail("jkelsy@gmail.com", 
                        "agendagoj2km@gmail.com", 
                        "AgendaGo, tarea vencida", texto);
            }            
        }
    }    
}