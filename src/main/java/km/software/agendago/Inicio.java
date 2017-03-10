/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package km.software.agendago;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import km.software.agenda.controller.UsuarioController;
import km.software.agendago.db.EstadoTarea;
import km.software.agendago.db.Permiso;
import km.software.agendago.db.Persona;
import km.software.agendago.db.Rol;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.EstadoTareaFacade;
import km.software.agendago.db.service.facade.PermisoFacade;
import km.software.agendago.db.service.facade.PersonaFacade;
import km.software.agendago.db.service.facade.RolFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;

@Startup
@Singleton
public class Inicio {

    @Inject
    private UsuarioFacade usuarioService;

    @Inject
    private PersonaFacade personaService;

    @Inject
    private RolFacade rolService;

    @Inject
    private PermisoFacade permisoService;

    @Inject
    private EstadoTareaFacade estadoService;

    @PostConstruct
    public void iniciar() {

        Rol rol = null;
        Usuario usuario = null;
        Permiso permiso = null;
        EstadoTarea estadoTarea = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nombre", "ADMIN");
        Optional<Rol> rolOptional = rolService.findSingleByNamedQuery("Rol.findByNombre", parameters, Rol.class);

        if (!rolOptional.isPresent()) {
            rol = new Rol();
            rol.setNombre("ADMIN");
            rolService.create(rol);

            parameters.clear();
            parameters.put("login", "admin");

            Optional<Usuario> usuarioOptional = usuarioService.findSingleByNamedQuery("Usuario.findByLogin", parameters, Usuario.class);
            if (!usuarioOptional.isPresent()) {
                MessageDigest md;
                String _password = "admin";
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    byte[] passwordBytes = _password.getBytes();
                    byte[] hash = md.digest(passwordBytes);
                    String passwordHash = Base64.getEncoder().encodeToString(hash);
                    
                    usuario = new Usuario();
                    usuario.setLogin("admin");
                    usuario.setPassword(passwordHash);
                    usuario.setActivo(true);
                    Persona persona = new Persona();
                    persona.setApellidos("admin");
                    persona.setNombre("admin");
                    personaService.create(persona);
                    usuario.setPersona(persona);
                    usuarioService.create(usuario);
                    
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }

            parameters.clear();
            parameters.put("usuarioID", usuario.getId());
            parameters.put("rolID", rol.getId());
            Optional<Permiso> permisoOptional = permisoService.findSingleByNamedQuery("Permiso.findByUsuarioAndRol", parameters, Permiso.class);

            if (!permisoOptional.isPresent()) {
                permiso = new Permiso();
                permiso.setUsuario(usuario);
                permiso.setRol(rol);
                permisoService.create(permiso);
            }
        }

        parameters.clear();
        parameters.put("nombre", "USER");
        Optional<Rol> rolUser = rolService.findSingleByNamedQuery("Rol.findByNombre", parameters, Rol.class);

        if (!rolUser.isPresent()) {
            rol = new Rol();
            rol.setNombre("USER");
            rolService.create(rol);
        }

        //creacion de los estados de las tareas
        parameters.clear();
        parameters.put("nombre", "ACTIVA");
        Optional<EstadoTarea> estadoOptional = estadoService.findSingleByNamedQuery("EstadoTarea.findByNombre", parameters, EstadoTarea.class);
        if (!estadoOptional.isPresent()) {
            estadoTarea = new EstadoTarea();
            estadoTarea.setNombre("ACTIVA");
            estadoService.create(estadoTarea);
        }
        
        parameters.clear();
        parameters.put("nombre", "ALERTA");
        Optional<EstadoTarea> estadoAlerta = estadoService.findSingleByNamedQuery("EstadoTarea.findByNombre", parameters, EstadoTarea.class);
        if (!estadoAlerta.isPresent()) {
            estadoTarea = new EstadoTarea();
            estadoTarea.setNombre("ALERTA");
            estadoService.create(estadoTarea);
        }
        
        parameters.clear();
        parameters.put("nombre", "VENCIDA");
        Optional<EstadoTarea> estadoOptional2 = estadoService.findSingleByNamedQuery("EstadoTarea.findByNombre", parameters, EstadoTarea.class);
        if (!estadoOptional2.isPresent()) {
            estadoTarea = new EstadoTarea();
            estadoTarea.setNombre("VENCIDA");
            estadoService.create(estadoTarea);
        }
        
        parameters.clear();
        parameters.put("nombre", "FINALIZADA");
        Optional<EstadoTarea> estadoOptional3 = estadoService.findSingleByNamedQuery("EstadoTarea.findByNombre", parameters, EstadoTarea.class);
        if (!estadoOptional3.isPresent()) {
            estadoTarea = new EstadoTarea();
            estadoTarea.setNombre("FINALIZADA");
            estadoService.create(estadoTarea);
        }
        
    }
}
