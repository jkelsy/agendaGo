package km.software.agendago;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.TareaFacade;
 
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
 
@ManagedBean
@ViewScoped
public class CalendarioBean implements Serializable {
 
    private ScheduleModel eventModel; 
    private ScheduleEvent event = new DefaultScheduleEvent();    
    @Inject private TareaFacade tareaService;   
    @Inject private AgendaFacade agendaService; 
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuarioId", agendaService.getUsuarioActual().getId());        
        List<Tarea> tareas = tareaService.findByNamedQuery("Tarea.findAllByResponsable", parameters);
        
        for(Tarea tareaInstance: tareas){
            DefaultScheduleEvent evento = new DefaultScheduleEvent();
            evento.setData(tareaInstance);
            evento.setTitle(tareaInstance.getDescripcion());
            evento.setDescription(tareaInstance.getDescripcion());
            evento.setStartDate(tareaInstance.getFechaFinal());
            evento.setEndDate(tareaInstance.getFechaFinal());
            evento.setStyleClass(tareaInstance.getEstado().getNombre());            
            eventModel.addEvent(evento);
        }
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}