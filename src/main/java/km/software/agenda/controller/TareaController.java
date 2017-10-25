package km.software.agenda.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import km.software.agendago.EmailTareaService;
import km.software.agendago.db.Archivo;
import km.software.agendago.db.ArchivoHistorial;
import km.software.agendago.db.ArchivoTarea;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.Historial;
import km.software.agendago.db.Observacion;
import km.software.agendago.db.Responsable;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.ArchivoFacade;
import km.software.agendago.db.service.facade.ArchivoHistorialFacade;
import km.software.agendago.db.service.facade.ArchivoTareaFacade;
import km.software.agendago.db.service.facade.EstadoTareaFacade;
import km.software.agendago.db.service.facade.HistorialFacade;
import km.software.agendago.db.service.facade.ObservacionFacade;
import km.software.agendago.db.service.facade.PermisoFacade;
import km.software.agendago.db.service.facade.PersonaFacade;
import km.software.agendago.db.service.facade.ResponsableFacade;
import km.software.agendago.db.service.facade.RolFacade;
import km.software.agendago.db.service.facade.TareaFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("tareaController")
@ViewScoped
public class TareaController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TareaController.class.getName()); 

    private Tarea tarea;
    private Historial historial;
    private List<Usuario> responsables;
    private List<ArchivoTarea> archivos;
    private List<Observacion> observaciones;
    private Long idTarea;
    private String periodo;
    private String observacion;
    private UploadedFile uploadedFile;
    
    @Inject 
    private ArchivoTareaFacade archivoTareaService;
    private List<Archivo> archivosTemporales;
    
    
    private boolean[] meses = {false, false, false, false, false, false,
                               false, false, false, false, false, false};
    
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
    
    @Inject 
    private EstadoTareaFacade estadoService;
    
    @Inject 
    private AgendaFacade agendaService;   
    
    @Inject
    private EmailTareaService emailService;
    
    @Inject
    private ArchivoFacade archivoService;
    
    @Inject 
    private HistorialFacade historialService;
    
    @Inject 
    private ArchivoHistorialFacade archivoHistorialService;
    
    @Inject 
    private ObservacionFacade observacionService;
    
    @PostConstruct
    public void cargar(){
        tarea = new Tarea();
        historial = new Historial();
        if(responsables != null && !responsables.isEmpty()){
            responsables.clear();
        }
        
        archivosTemporales = new ArrayList<>();
    }

    public boolean[] getMeses() {
        return meses;
    }

    public void setMeses(boolean[] meses) {
        this.meses = meses;
    }

    public List<ArchivoTarea> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoTarea> archivos) {
        this.archivos = archivos;
    }    

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }    

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }
    
    public void cargarEditarTarea(){
        tarea = tareaService.find(idTarea);
        responsables = agendaService.findUsuariosByTarea(idTarea);
    }
    
    public void cargarObservacionTarea(){
        tarea = tareaService.find(idTarea);
        observacion = "";        
    }
    
    public void cargarVerTarea(){
        tarea = tareaService.find(idTarea);
        responsables = agendaService.findUsuariosByTarea(idTarea);
        archivos = agendaService.findArchivosByTarea(idTarea);
        observaciones = agendaService.findObservacionByTarea(idTarea);
               
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    } 

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
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
    
    public String guardarTarea(){   
        
        if(responsables == null){
            FacesContext context = FacesContext.getCurrentInstance();     
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",  "Debe seleccionar al menos un responsable") );
            return "";
        }
        
        java.util.Date hoy = new java.util.Date();
        
        EstadoTarea estadoActiva = agendaService.findEstadoTareaByNombre("ACTIVA");
        EstadoTarea estadoAlerta = agendaService.findEstadoTareaByNombre("ALERTA");
        EstadoTarea estadoVencida = agendaService.findEstadoTareaByNombre("VENCIDA"); 
        
        Usuario creador = agendaService.getUsuarioActual(); 
        tarea.setCreador(creador);
        tarea.setFechaCreacion(hoy);
        tarea.setFechaInicial(hoy);
        
        if(hoy.after(tarea.getFechaFinal())){
            tarea.setEstado(estadoVencida);
        }else if(hoy.after(tarea.getFechaAviso())){
            tarea.setEstado(estadoAlerta);
        }else{
            tarea.setEstado(estadoActiva);
        }
        tareaService.create(tarea);
        
        historial.setEstado(tarea.getEstado());
        historial.setFecha(hoy);
        historial.setUsuario(creador);
        historial.setTarea(tarea);
        historial.setObservacion(tarea.getDescripcion());
        historialService.create(historial);
        
        //guardar los archivos temporales
        for (Archivo at : archivosTemporales){
            ArchivoTarea archivoTarea = new ArchivoTarea();
            archivoTarea.setArchivo(at);
            archivoTarea.setTarea(tarea);
            archivoTarea.setUsuario(agendaService.getUsuarioActual());
            archivoTarea.setFecha(new java.util.Date());
            archivoTareaService.create(archivoTarea);
        }
        
        String txtResponsables = "";        
        
        for (Usuario usr : responsables) {
            Responsable responsable = new Responsable();
            responsable.setUsuario(usr);
            responsable.setTarea(tarea);
            responsableService.create(responsable);     
            
            txtResponsables += usr.getPersona().getNombre()+ " "
                +usr.getPersona().getApellidos()+", ";
        }
        
        for (Usuario usr : responsables) {
            String texto = "<b>AgendaGo</b>";
                texto += "<br/>Señor: <b>"+usr.getPersona().getNombre() +"</b>";
                texto += "<br/>Le informamos que tiene una terea asignada";
                texto += "<br/>Descripción: "+tarea.getDescripcion();
                texto += "<br/>Fecha Inicial: "+ tarea.getFechaInicial();
                texto += "<br/>Fecha Final: "+ tarea.getFechaFinal();
                texto += "<br/>Responsables: "+ txtResponsables;
                texto += "<br/>Creador: "+tarea.getCreador().getLogin();
                
                emailService.sendEmail(usr.getPersona().getEmail(), 
                        "agendagoj2km@gmail.com", 
                        "AgendaGo, tarea asignada", texto);
        } 
        
        int i = 0;
        Calendar calFechaInicial = Calendar.getInstance();
        calFechaInicial.setTime(tarea.getFechaInicial()); 
        
        Calendar calFechaAviso = Calendar.getInstance();
        calFechaAviso.setTime(tarea.getFechaAviso());
        
        Calendar calFechaFinal = Calendar.getInstance();
        calFechaFinal.setTime(tarea.getFechaFinal());
        
        Tarea tareaTempo;
        
        long diferenciaAviso = ChronoUnit.DAYS.between(calFechaInicial.toInstant(), calFechaAviso.toInstant() );
        long diferenciaFinal = ChronoUnit.DAYS.between(calFechaInicial.toInstant(), calFechaFinal.toInstant());
        
        int diferenciaAvisoInteger = (int) diferenciaAviso;
        int diferenciaFinalInteger = (int) diferenciaFinal; 
        for(boolean mes : meses){            
            if(mes){                
                   
                calFechaInicial.set(Calendar.MONTH, i);
                
                tareaTempo = new Tarea();
                tareaTempo.setCreador(tarea.getCreador());
                tareaTempo.setDescripcion(tarea.getDescripcion());
                tareaTempo.setEstado(tarea.getEstado());
                tareaTempo.setFechaCreacion(tarea.getFechaCreacion());
                tareaTempo.setFechaInicial(calFechaInicial.getTime());
                
                calFechaAviso.setTime(calFechaInicial.getTime());
                calFechaAviso.add(Calendar.DATE,  diferenciaAvisoInteger);
                tareaTempo.setFechaAviso(calFechaAviso.getTime());
                
                calFechaFinal.setTime(calFechaInicial.getTime());
                calFechaFinal.add(Calendar.DATE,  diferenciaFinalInteger);
                tareaTempo.setFechaFinal(calFechaFinal.getTime()); 
                
                tareaService.create(tareaTempo);
                
                Historial historialTempo = new Historial();
                historialTempo.setEstado(tareaTempo.getEstado());
                historialTempo.setFecha(hoy);
                historialTempo.setUsuario(creador);
                historialTempo.setTarea(tareaTempo);
                historialTempo.setObservacion(tareaTempo.getDescripcion());
                historialService.create(historialTempo);
                
                for (Usuario usr : responsables) {
                    Responsable responsable = new Responsable();
                    responsable.setUsuario(usr);
                    responsable.setTarea(tareaTempo);
                    responsableService.create(responsable);                    
                }
            }
            
            i++;
        }
        
        if(responsables != null && !responsables.isEmpty()){
            responsables.clear();
        }
        
        return "/pages/main";                
    }   
    
    public void cargarArchivos(FileUploadEvent event) {
        UploadedFile file = event.getFile();  
        
        //se puede mejorar utilizando el usuario actual,
        //para evitar la concurrencia
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Calendar ahora = Calendar.getInstance();
        String id = sdf.format(ahora.getTime());
        
        if(file.getContents().length != 0){
            String ruta = "/home/files/agendaGo/"+id+"_"+file.getFileName().replaceAll(" ", "_");
            try(InputStream is = file.getInputstream(); 
                OutputStream out = new FileOutputStream(new File(ruta))){
                
                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            }catch(IOException e){
                logger.log(Level.SEVERE, "Error de lectura de archivo", e);
            }
            
            Archivo archivo = new Archivo();
            archivo.setNombre(id+"_"+file.getFileName().replaceAll(" ", "_"));
            archivo.setExtension(file.getContentType());            
            archivoService.create(archivo);            
            archivosTemporales.add(archivo);            
                             
        }        
    }
    
    public String guardarObservacion(){
        
        Observacion observacionInstance = new Observacion();
        observacionInstance.setUsuario(agendaService.getUsuarioActual());
        observacionInstance.setFecha(new java.util.Date());
        observacionInstance.setTexto(this.observacion);        
        observacionInstance.setTarea(tarea);
        observacionService.create(observacionInstance);
        
        String texto = "<b>AgendaGo</b>";
        texto += "<br/>Señor: <b>"+tarea.getCreador().getPersona().getNombre() +"</b>";
        texto += "<br/>Le informamos que tiene una observación de la terea creada";
        texto += "<br/>Descripción: "+this.observacion;                
        texto += "<br/>Observación creada por: "+ agendaService.getUsuarioActual().getLogin();
        emailService.sendEmail(tarea.getCreador().getPersona().getEmail(), 
                "agendagoj2km@gmail.com", 
                "AgendaGo, Observación creada", texto);                
        return "/pages/main";   
    }

    public String editarTarea(){       
        java.util.Date hoy = new java.util.Date();        
        EstadoTarea estadoActiva = agendaService.findEstadoTareaByNombre("ACTIVA");
        EstadoTarea estadoAlerta = agendaService.findEstadoTareaByNombre("ALERTA");
        EstadoTarea estadoVencida = agendaService.findEstadoTareaByNombre("VENCIDA");
        
        if(hoy.after(tarea.getFechaFinal())){
            tarea.setEstado(estadoVencida);
        }else if(hoy.after(tarea.getFechaAviso())){
            tarea.setEstado(estadoAlerta);
        }else{
            tarea.setEstado(estadoActiva);
        }
        
        tareaService.edit(tarea);
        
        List<Responsable> _responsables = agendaService.findResponsablesByTarea(tarea.getId());
        for(Responsable _res: _responsables){
            responsableService.remove(_res);
        }
        
        for (Usuario usr : responsables) {
            Responsable responsable = new Responsable();
            responsable.setUsuario(usr);
            responsable.setTarea(tarea);
            responsableService.create(responsable);            
        }
        
        return "/pages/main";                
    }    
    
    public List<Usuario> completeUsuario(String query) {
        List<Usuario> allUsuarios = usuarioService.findAll();
        List<Usuario> filteredUsuarios = new ArrayList<>();
         
        for (int i = 0; i < allUsuarios.size(); i++) {
            Usuario usr = allUsuarios.get(i);
            if(usr.getLogin().toLowerCase().startsWith(query.toLowerCase())) {
                filteredUsuarios.add(usr);
            }
        }         
        return filteredUsuarios;
    }  
   
}