/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package km.software.agenda.controller;

import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.UsuarioFacade;
 

/**
 *
 * @author jkelsy
 */
@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter{
    
   @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        
        if(value != null && value.trim().length() > 0) {
            try {
                UsuarioFacade usuarioService = fc.getCurrentInstance().getApplication().evaluateExpressionGet(fc, "#{usuario}", UsuarioFacade.class);
                System.out.println("++++++"+usuarioService);
                Usuario usr = usuarioService.find(Long.parseLong(value));
                return usr;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid usuario."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Usuario) object).getId());
        }
        else {
            return null;
        }
    } 
    
}
