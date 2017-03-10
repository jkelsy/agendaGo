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
import km.software.agendago.db.Rol;
import km.software.agendago.db.Usuario;
import km.software.agendago.db.service.facade.RolFacade;
import km.software.agendago.db.service.facade.UsuarioFacade;
 

/**
 *
 * @author jkelsy
 */
@FacesConverter("rolConverter")
public class RolConverter implements Converter{
    
   @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        
        if(value != null && value.trim().length() > 0) {
            try {
                RolFacade rolService= fc.getCurrentInstance().getApplication().evaluateExpressionGet(fc, "#{rol}", RolFacade.class);
                
                Rol _rol= rolService.find(Long.parseLong(value));
                return _rol;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid rol."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Rol) object).getId());
        }
        else {
            return null;
        }
    } 
    
}
