package km.software.agendago.db.service.facade;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import km.software.agendago.db.ArchivoTarea;
import km.software.agendago.db.service.facade.AbstractFacade;

@Stateless
@Named("archivoTarea")
public class ArchivoTareaFacade extends AbstractFacade<ArchivoTarea> {

    @PersistenceContext(unitName = "agendaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoTareaFacade() {
        super(ArchivoTarea.class);
    }

}
