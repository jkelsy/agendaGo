package km.software.agenda.controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import km.software.agendago.db.Permiso;
import km.software.agendago.db.Persona;
import km.software.agendago.db.Rol;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.PermisoFacade;
import km.software.agendago.db.service.facade.PersonaFacade;
import km.software.agendago.db.service.facade.RolFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;

@Named("usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName()); 
    private Usuario usuario;
    
    //modificar password    
    private String actual;
    private String password;
    private String confirmar; 
    
    private Persona persona;
    private List<Rol> permisos;
    private Long idUsuario;    
    
    @Inject
    private PersonaFacade personaService;
    
    @Inject
    private UsuarioFacade usuarioService;
    
    @Inject
    private PermisoFacade permisoService;
    
    @Inject
    private RolFacade rolService;
    
    
    @PostConstruct
    public void cargar(){
        usuario = new Usuario();
        persona = new Persona();
        
        if(permisos != null && !permisos.isEmpty()){
            permisos.clear();
        }
        
    } 
    
    public void cargarEditarUsuario(){
        usuario = usuarioService.find(idUsuario);
        persona = usuario.getPersona();        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("usuarioID", usuario.getId());
        permisos = rolService.findByNamedQuery("Permiso.findAllRolesByUsuario", parameters);
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }    

    public Usuario getUsuario() {        
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    } 

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmar() {
        return confirmar;
    }

    public void setConfirmar(String confirmar) {
        this.confirmar = confirmar;
    }

    public Persona getPersona() {        
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Rol> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Rol> permisos) {
        this.permisos = permisos;
    }
    
    
    public String seleccionarUsuario(Usuario usuario){        
        return "/pages/admin/editarUsuarioForm?faces-redirect=true&amp&id="+usuario.getId();
    }
    
    public String cambiarPassword(Usuario usuario){        
        return "/pages/admin/passwordUsuarioForm?faces-redirect=true&amp&id="+usuario.getId();
    }
    
    public String nuevoUsuario(){        
        return "/pages/admin/nuevoUsuarioForm";
    }
    
    public String guardarUsuario(){
        if(!password.isEmpty() && password.equals(confirmar)){
            
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-256");
                byte[] passwordBytes = password.getBytes();
                byte[] hash = md.digest(passwordBytes);
                String passwordHash = Base64.getEncoder().encodeToString(hash);
                
                usuario.setPassword(passwordHash);
                personaService.create(persona);
                usuario.setActivo(true);
                usuario.setPersona(persona);
                usuarioService.create(usuario);   

                for(Rol _rol: permisos){
                    Permiso permisoInstance = new Permiso();
                    permisoInstance.setRol(_rol);
                    permisoInstance.setUsuario(usuario);
                    permisoService.create(permisoInstance);
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            FacesContext context = FacesContext.getCurrentInstance();     
            context.addMessage(null, new FacesMessage("Error",  "Error al ingresar el password") );
            return "/pages/admin/nuevoUsuarioForm";
        }        
        
        return "/pages/admin/usuarioList"; 
    }
    

    
    public void modificarUsuario() throws IOException{
        
        if(persona.getNombre().isEmpty()){
            FacesMessage msg = new FacesMessage();
            msg.setSummary("Editar Usuario");
            msg.setDetail("Debe ingresar un nombre para el usuario");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        usuarioService.edit(usuario);            
        personaService.edit(persona); 
        //eliminamos los permisos actuales
        List<Permiso> permisoList = findAllPermisosByUsuario(usuario);
        for(Permiso _permiso: permisoList){
            permisoService.remove(_permiso);
        }

        //asignamos los permisos seleccionados
        for(Rol _rol: permisos){
            Permiso permisoInstance = new Permiso();
            permisoInstance.setRol(_rol);
            permisoInstance.setUsuario(usuario);
            permisoService.create(permisoInstance);
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Editar Usuario");
        msg.setDetail("Usuario editado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);   
        FacesContext.getCurrentInstance().getExternalContext().redirect("usuarioList.xhtml");        
    }
    
    //método con el que el administrador cambia la contraseña de cualqueir usuario
    public void cambiarContrasenya() throws IOException{
        if(!password.isEmpty()){
            if(password.equals(confirmar)){
                MessageDigest md;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    byte[] passwordBytes = password.getBytes();
                    byte[] hash = md.digest(passwordBytes);
                    String passwordHash = Base64.getEncoder().encodeToString(hash);
                    usuario.setPassword(passwordHash);
                    usuarioService.edit(usuario);
                    FacesMessage msg = new FacesMessage();
                    msg.setSummary("Cambiar Contraseña");
                    msg.setDetail("Contraseña cambiada con éxito");
                    FacesContext.getCurrentInstance().addMessage(null, msg); 
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("usuarioList.xhtml");

                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{                
                FacesMessage msg = new FacesMessage();
                msg.setSummary("Error");
                msg.setDetail("Error al ingresar las contraseñas");
                FacesContext.getCurrentInstance().addMessage(null, msg);             
                
            }
        }
    }
    
    //método con el que todos los usuarios pueden cambiar su contraseña
    public void cambiarContrasenyaActual() throws IOException{        
        
        MessageDigest md;
        
        try {
            md = MessageDigest.getInstance("SHA-256");
        
            if(actual.isEmpty()){
                FacesContext context = FacesContext.getCurrentInstance();     
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",  "Debe Ingresar la contraseña actual") );            
                return;
            }       
            
            if(password.isEmpty()){
                FacesContext context = FacesContext.getCurrentInstance();     
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",  "Debe Ingresar la nueva contraseña") );            
                return;
            }
            
            if(confirmar.isEmpty()){
                FacesContext context = FacesContext.getCurrentInstance();     
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",  "Debe confirmar la nueva contraseña") );            
                return;
            }
            
            byte[] actualBytes = actual.getBytes();
            byte[] actualHash = md.digest(actualBytes);
            String passwordActualHash = Base64.getEncoder().encodeToString(actualHash);

            if(!password.isEmpty()){
                if(password.equals(confirmar)){                       
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.clear();
                    parameters.put("login", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());

                    Optional<Usuario> usuarioOptional = usuarioService.findSingleByNamedQuery("Usuario.findByLogin", parameters, Usuario.class);
                    
                    if (usuarioOptional.isPresent()) {
                        if(usuarioOptional.get().getPassword().equals(passwordActualHash)){
                            
                            byte[] passwordBytes = password.getBytes();
                            byte[] hash = md.digest(passwordBytes);
                            String passwordHash = Base64.getEncoder().encodeToString(hash);
                            usuario = usuarioOptional.get();
                            usuario.setPassword(passwordHash);
                            usuarioService.edit(usuario);
                            FacesMessage msg = new FacesMessage();
                            msg.setSummary("Cambiar Contraseña");
                            msg.setDetail("Contraseña cambiada con éxito");
                            FacesContext.getCurrentInstance().addMessage(null, msg); 
                            //FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                            //FacesContext.getCurrentInstance().getExternalContext().redirect("usuarioList.xhtml");
                        }else{
                            FacesMessage msg = new FacesMessage();
                            msg.setSummary("Error al ingresar la contraseña actual");
                            msg.setDetail("Error al ingresar la contraseña actual");
                            FacesContext.getCurrentInstance().addMessage(null, msg);                            
                        }
                    }
                }else{                
                    FacesMessage msg = new FacesMessage();
                    msg.setSummary("Error");
                    msg.setDetail("Error al ingresar las contraseñas");
                    FacesContext.getCurrentInstance().addMessage(null, msg);             

                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Permiso> findAllPermisosByUsuario(Usuario usuario){
        Map<String, Object> parameters = new HashMap();
        parameters.put("usuarioID", usuario.getId());
        List<Permiso> permisos = permisoService.findByNamedQuery("Permiso.findAllByUsuario", parameters);
        return permisos;
    }
    
    public List<Rol> completeRol(String query) {
        List<Rol> allRoles = rolService.findAll();
        List<Rol> filteredRoles = new ArrayList<>();
         
        for (int i = 0; i < allRoles.size(); i++) {
            Rol rol = allRoles.get(i);
            if(rol.getNombre().toLowerCase().startsWith(query)) {
                filteredRoles.add(rol);
            }
        }         
        return filteredRoles;
    }
}