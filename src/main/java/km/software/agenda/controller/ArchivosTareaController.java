package km.software.agenda.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import km.software.agendago.db.Archivo;
import km.software.agendago.db.ArchivoTarea;
import km.software.agendago.db.Tarea;
import km.software.agendago.db.service.facade.AgendaFacade;
import km.software.agendago.db.service.facade.ArchivoFacade;
import km.software.agendago.db.service.facade.ArchivoTareaFacade;
import km.software.agendago.db.service.facade.HistorialFacade;
import km.software.agendago.db.service.facade.TareaFacade;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("archivosTareaController")
@ViewScoped
public class ArchivosTareaController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ArchivosTareaController.class.getName()); 
    
    private List<ArchivoTarea> archivos;

    private Tarea tarea;    
    private Long idTarea; 
    
    @Inject 
    private TareaFacade tareaService;    
    
    @Inject
    private ArchivoFacade archivoService;
    
    @Inject 
    private HistorialFacade historialService;
    
    @Inject 
    private ArchivoTareaFacade archivoTareaService;
    
    @Inject
    private AgendaFacade agendaService;
    
    public void cargarArchivosTarea(){
        tarea = tareaService.find(idTarea);
        cargarArchivos();
    }
    
    public void cargarArchivos(){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tareaID", tarea.getId());
        archivos = archivoTareaService.findByNamedQuery("ArchivoTarea.findAllByTarea", parameters);
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }  

    public List<ArchivoTarea> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoTarea> archivos) {
        this.archivos = archivos;
    }  
    
    public void eliminarArchivo(ArchivoTarea archivoTarea){
        archivoTareaService.remove(archivoTarea);
        archivoService.remove(archivoTarea.getArchivo());        
        cargarArchivos();
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();        
        
        if(file.getContents().length != 0){
            String ruta = "/home/files/agendaGo/"+tarea.getId()+"_"+file.getFileName().replaceAll(" ", "_");
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
            archivo.setNombre(tarea.getId()+"_"+file.getFileName().replaceAll(" ", "_"));
            archivo.setExtension(file.getContentType());            
            archivoService.create(archivo);
            
            ArchivoTarea archivoTarea = new ArchivoTarea();
            archivoTarea.setArchivo(archivo);
            archivoTarea.setTarea(tarea);
            archivoTarea.setUsuario(agendaService.getUsuarioActual());
            archivoTarea.setFecha(new java.util.Date());
            archivoTareaService.create(archivoTarea);
            
            cargarArchivos();            
        }        
    }   
}